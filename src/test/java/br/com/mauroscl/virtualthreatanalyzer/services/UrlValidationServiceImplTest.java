package br.com.mauroscl.virtualthreatanalyzer.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import br.com.mauroscl.virtualthreatanalyzer.infra.WhiteListRuleRepository;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UrlValidationServiceImplTest {

  private static final String REGEX = "[a-z]+\\.axur\\.[a-z]+";

  @InjectMocks
  private UrlValidationServiceImpl urlValidationService;

  @Mock
  private WhiteListRuleRepository repository;

  @BeforeEach
  void mockRepository() {

    final WhiteListRule whiteListRule = new WhiteListRule();
    whiteListRule.setRegex(REGEX);
    when(repository.findRulesAvailableForClient(anyString()))
        .thenReturn(Collections.singletonList(whiteListRule));
  }

  @Test
  void deveRetornarMatchQuandoUrlEstiverNaWhiteList(){
    final ValidationCommand validationCommand = new ValidationCommand();
    validationCommand.setClient("mauro");
    validationCommand.setUrl("www.axur.com");
    validationCommand.setCorrelationId(1234);
    final UrlValidationResponse response = urlValidationService.validar(validationCommand);

    assertThat(response.getCorrelationId()).isEqualTo(1234);
    assertThat(response.isMatch()).isTrue();
    assertThat(response.getRegex()).isEqualTo(REGEX);
  }

  @Test
  public void deveRetornarUnmatchQuandoUrlNaoEstiverNaWhiteList() {
    final ValidationCommand validationCommand = new ValidationCommand();
    validationCommand.setClient("mauro");
    validationCommand.setUrl("www.google.com");
    validationCommand.setCorrelationId(1234);
    final UrlValidationResponse response = urlValidationService.validar(validationCommand);

    assertThat(response.getCorrelationId()).isEqualTo(1234);
    assertThat(response.isMatch()).isFalse();
    assertThat(response.getRegex()).isNull();
  }

}