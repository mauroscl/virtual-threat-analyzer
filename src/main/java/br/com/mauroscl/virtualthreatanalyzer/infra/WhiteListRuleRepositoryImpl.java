package br.com.mauroscl.virtualthreatanalyzer.infra;

import br.com.mauroscl.virtualthreatanalyzer.services.WhiteListRule;
import br.com.mauroscl.virtualthreatanalyzer.services.WhiteListRuleRepository;
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
  public List<String> findRulesAvailableForClient(final String client) {
    return internalRepository.findByClientOrClientIsNull(client);
  }

  @Override
  public void save(final WhiteListRule rule) {
    internalRepository.save(rule);
  }

}
