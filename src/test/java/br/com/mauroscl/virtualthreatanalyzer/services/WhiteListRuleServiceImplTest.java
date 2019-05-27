package br.com.mauroscl.virtualthreatanalyzer.services;

import br.com.mauroscl.virtualthreatanalyzer.infra.WhiteListRuleRepository;
import br.com.mauroscl.virtualthreatanalyzer.model.WhiteListRule;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;

@ExtendWith(MockitoExtension.class)
class WhiteListRuleServiceImplTest {

  @Mock
  private WhiteListRuleRepository repository;

  @InjectMocks
  WhiteListRuleServiceImpl service;

  @Test
  void deveCriarRegraGlobalQuandoNaoForInformadoCliente() {
    final WhiteListRule whiteListRule = new WhiteListRule();
    whiteListRule.setRegex("w{3}\\.axur\\.com");
    service.createNew(whiteListRule);
    Mockito.verify(repository, Mockito.times(1)).saveGlobalRule(Mockito.any(WhiteListRule.class));
    Mockito.verify(repository, Mockito.never()).saveClientRule(Mockito.any(WhiteListRule.class));
  }

  @Test
  void deveCriarRegraDeCliente() {
    final WhiteListRule whiteListRule = new WhiteListRule();
    whiteListRule.setClient("mauro");
    whiteListRule.setRegex("w{3}\\.axur\\.com");
    service.createNew(whiteListRule);
    Mockito.verify(repository, Mockito.times(1)).saveClientRule(Mockito.any(WhiteListRule.class));
    Mockito.verify(repository, Mockito.never()).saveGlobalRule(Mockito.any(WhiteListRule.class));
  }

  private static Stream<Arguments> testParams() {
    return Stream.of(
        Arguments.of("qtqwtqteqtqtqtqwetqwtqwetqetewttqwtqtqetqtqewtqetwetqwteqqtqwtqtqwtqtqwetqetewtqetqteqteqtetqeqtewqteqtqtqteqwettqeqtewettqtqteaa", "www.axur.com"),
        Arguments.of("mauro", null),
        Arguments.of(null, "qtqwtqteqtqtqtqwetqwtqwetqetewttqwtqtqetqtqewtqetwetqwteqqtqwtqtqwtqtqwetqetewtqetqteqteqtetqeqtewqteqtqtqteqwettqeqtewettqtqteaa"));
  }

  @ParameterizedTest()
  @MethodSource("testParams")
  void deveRejeitarMensagemComDadosNaoPreenchidos(String client, String regex) {
    final WhiteListRule whiteListRule = new WhiteListRule();
    whiteListRule.setClient(client);
    whiteListRule.setRegex(regex);
    Assertions.assertThrows(AmqpRejectAndDontRequeueException.class,
        () -> service.createNew(whiteListRule));
  }


}