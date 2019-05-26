package br.com.mauroscl.virtualthreatanalyzer.services;

import br.com.mauroscl.virtualthreatanalyzer.infra.WhiteListRuleRepository;
import org.springframework.stereotype.Service;

@Service
public class WhiteListRuleService {

  private final WhiteListRuleRepository repository;

  public WhiteListRuleService(final WhiteListRuleRepository repository) {
    this.repository = repository;
  }

  void salvar(WhiteListRule whiteListRule) {
    if (whiteListRule.getClient() == null) {
      repository.saveGlobalRule(whiteListRule);
    } else{
      repository.saveClientRule(whiteListRule);
    }

  }

}
