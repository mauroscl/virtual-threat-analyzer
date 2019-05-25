package br.com.mauroscl.virtualthreatanalyzer.services;

import br.com.mauroscl.virtualthreatanalyzer.infra.WhiteListRuleRepository;
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

    List<String> rules = repository.findRulesAvailableForClient(command.getClient());

    return rules.parallelStream().filter(command.getUrl()::matches)
        .findFirst()
        .map(rule -> UrlValidationResponse.forMatch(command.getCorrelationId(), rule))
        .orElse(UrlValidationResponse.forUnmatch(command.getCorrelationId()));

  }
}
