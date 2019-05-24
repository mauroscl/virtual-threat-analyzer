package br.com.mauroscl.virtualthreatanalyzer.services;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UrlValidationServiceImpl implements UrlValidationService {

  private final WhiteListRuleRepository repository;


  public UrlValidationServiceImpl(final WhiteListRuleRepository repository) {
    this.repository = repository;
  }

  @Override
  public UrlValidationResponse validar(ValidationCommand command) {

    List<WhiteListRule> rules = repository.findRulesAvailableForClient(command.getClient());

    return rules.parallelStream().filter(r -> command.getUrl().matches(r.getRegex()))
        .findFirst()
        .map(rule -> UrlValidationResponse.forMatch(command.getCorrelationId(), rule.getRegex()))
        .orElse(UrlValidationResponse.forUnmatch(command.getCorrelationId()));

  }
}
