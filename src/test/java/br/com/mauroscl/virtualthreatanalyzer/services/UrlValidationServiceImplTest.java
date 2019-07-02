package br.com.mauroscl.virtualthreatanalyzer.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import br.com.mauroscl.virtualthreatanalyzer.infra.WhiteListRuleRepository;
import br.com.mauroscl.virtualthreatanalyzer.model.UrlValidationResponse;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.hibernate.cache.ehcache.internal.HibernateEhcacheUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.data.jpa.provider.HibernateUtils;

@ExtendWith(MockitoExtension.class)
class UrlValidationServiceImplTest {

  private static final String REGEX = "[a-z]+\\.axur\\.[a-z]+";


  @InjectMocks
  private UrlValidationServiceImpl urlValidationService;

  @Mock
  private WhiteListRuleRepository repository;

  @Test
  void deveRetornarMatchQuandoUrlEstiverNaWhiteList() {

    when(repository.findRulesAvailableForClient(anyString()))
        .thenReturn(Collections.singletonList(REGEX));

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
  void deveRetornarUnmatchQuandoUrlNaoEstiverNaWhiteList() {
    final ValidationCommand validationCommand = new ValidationCommand();
    validationCommand.setClient("mauro");
    validationCommand.setUrl("www.google.com");
    validationCommand.setCorrelationId(1234);
    final UrlValidationResponse response = urlValidationService.validar(validationCommand);

    assertThat(response.getCorrelationId()).isEqualTo(1234);
    assertThat(response.isMatch()).isFalse();
    assertThat(response.getRegex()).isNull();
  }

  private static Stream<Arguments> testParams() {
    return Stream.of(
        Arguments.of("mauro", null),
        Arguments.of(null, "www.axur.com") );
  }

  @ParameterizedTest()
  @MethodSource("testParams")
  void deveRejeitarMensagemComDadosNaoPreenchidos(String client, String url) {

    Pattern.compile("s");

    final ValidationCommand validationCommand = new ValidationCommand();
    validationCommand.setClient(client);
    validationCommand.setUrl(url);
    Assertions.assertThrows(AmqpRejectAndDontRequeueException.class,
        () -> urlValidationService.validar(validationCommand));
  }

}