package br.com.mauroscl.virtualthreatanalyzer.infra;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.mauroscl.virtualthreatanalyzer.services.ValidationConsumer;
import br.com.mauroscl.virtualthreatanalyzer.services.WhiteListRule;
import br.com.mauroscl.virtualthreatanalyzer.services.WhiteListRuleRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@EnableAutoConfiguration(exclude = RabbitAutoConfiguration.class)
class WhiteListRuleRepositoryImplTest {

  @MockBean
  private RabbitTemplate rabbitTemplate;

  @Autowired
  private WhiteListRuleRepository repository;

  @Test
  void deveInserirRegra() {
    final WhiteListRule rule = new WhiteListRule();
    rule.setClient("mauro");
    rule.setRegex("xxx");

    repository.save(rule);

    assertThat(rule.getId()).isNotNull();

  }

  @Test
  public void deveRetornarRegrasDisponiveisParaUmCliente() {
    inserirRegrasParaConsulta();
    final List<String> rules = repository.findRulesAvailableForClient("mauro");
    assertThat(rules.size()).isEqualTo(2);
  }

  @Test
  public void naoDeveRetornarRegrasParaClienteNaoCadastrado() {
    final WhiteListRule rule = new WhiteListRule();
    rule.setClient("mauro");
    rule.setRegex("xxx");

    repository.save(rule);

    final List<String> rules = repository.findRulesAvailableForClient("joao");

    assertThat(rules).isEmpty();

  }

  private void inserirRegrasParaConsulta() {
    final WhiteListRule globalRule = new WhiteListRule();
    globalRule.setRegex("xxx");

    repository.save(globalRule);

    final WhiteListRule clientRule = new WhiteListRule();
    clientRule.setClient("mauro");
    clientRule.setRegex("yyy");

    repository.save(clientRule);
  }

}