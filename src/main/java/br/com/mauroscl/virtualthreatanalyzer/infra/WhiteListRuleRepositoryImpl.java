package br.com.mauroscl.virtualthreatanalyzer.infra;

import br.com.mauroscl.virtualthreatanalyzer.services.WhiteListRuleRepository;
import br.com.mauroscl.virtualthreatanalyzer.services.WhiteListRule;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class WhiteListRuleRepositoryImpl implements WhiteListRuleRepository {

  private final SpringDataRepository internalRepository;

  @Autowired
  public WhiteListRuleRepositoryImpl(final SpringDataRepository internalRepository) {
    this.internalRepository = internalRepository;
  }

  @Override
  public List<WhiteListRule> findRulesAvailableForClient(final String client) {
    return internalRepository.findByClientOrClientIsNull(client);
  }

  @Override
  public List<WhiteListRule> findGlobalRules() {
    return internalRepository.findByClientIsNull();
  }

  @Override
  public void save(final WhiteListRule rule) {
    internalRepository.save(rule);
  }

}
