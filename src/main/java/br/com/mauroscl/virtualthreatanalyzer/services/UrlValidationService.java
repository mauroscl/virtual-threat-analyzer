package br.com.mauroscl.virtualthreatanalyzer.services;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UrlValidationService {
  private final WhiteListRuleRepository repository;

  public UrlValidationService(final WhiteListRuleRepository repository) {
    this.repository = repository;
  }

  void validar(ValidationCommand command) {

    List<WhiteListRule> rules = command.getClient() == null
        ? repository.findGlobalRules()
        : repository.findRulesAvailableForClient(command.getClient());

    rules
        .parallelStream()
        .filter(r -> command.getUrl().matches(r.getRegex()))
        .findFirst()
        .ifPresent(r -> new UrlValidationResponse());

  }
}
