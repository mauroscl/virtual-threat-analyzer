package br.com.mauroscl.virtualthreatanalyzer.services;

import br.com.mauroscl.virtualthreatanalyzer.infra.WhiteListRuleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UrlValidationServiceImpl implements UrlValidationService {

  private final WhiteListRuleRepository repository;


  public UrlValidationServiceImpl(final WhiteListRuleRepository repository) {
    this.repository = repository;
  }

  @Override
  public UrlValidationResponse validar(ValidationCommand command) {

    return findRule(repository.findRulesAvailableForClient(command.getClient()), command.getUrl())
        .map(rule -> UrlValidationResponse.forMatch(command.getCorrelationId(), rule))
        .orElseGet(
            () -> findRule(repository.findGlobalRules(), command.getUrl())
                .map(rule -> UrlValidationResponse.forMatch(command.getCorrelationId(), rule))
                .orElseGet(() -> UrlValidationResponse.forUnmatch(command.getCorrelationId())));

  }

  private Optional<String> findRule(List<String> availableRules, String url) {
    return availableRules
        .parallelStream()
        .filter(url::matches).findFirst();
  }

}
