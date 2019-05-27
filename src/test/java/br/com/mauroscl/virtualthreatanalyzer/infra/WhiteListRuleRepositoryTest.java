package br.com.mauroscl.virtualthreatanalyzer.infra;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.mauroscl.virtualthreatanalyzer.model.WhiteListRule;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class WhiteListRuleRepositoryTest {

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private WhiteListRuleRepository repository;

  @Test
  void deveInserirRegraDeCliente() {
    final WhiteListRule rule = new WhiteListRule();
    rule.setClient("mauro");
    rule.setRegex("xxx");

    repository.saveClientRule(rule);

    assertThat(rule.getId()).isNotNull();

  }

  @Test
  public void deveInserirRegraGlobal() {
    final WhiteListRule rule = new WhiteListRule();
    rule.setRegex("xxx");

    repository.saveGlobalRule(rule);

    assertThat(rule.getId()).isNotNull();

  }

  @Test
  public void deveRetornarRegrasDisponiveisParaUmCliente() {
    inserirRegrasParaConsulta();
    final List<String> rules = repository.findRulesAvailableForClient("mauro");
    assertThat(rules.size()).isEqualTo(1);
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

    entityManager.persist(globalRule);

    final WhiteListRule clientRule = new WhiteListRule();
    clientRule.setClient("mauro");
    clientRule.setRegex("yyy");

    entityManager.persist(clientRule);
  }

}