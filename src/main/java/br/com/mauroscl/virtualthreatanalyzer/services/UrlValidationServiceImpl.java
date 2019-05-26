package br.com.mauroscl.virtualthreatanalyzer.services;

import br.com.mauroscl.virtualthreatanalyzer.infra.WhiteListRuleRepository;
import java.util.stream.Stream;
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

    UrlValidationResponse response;
    try (Stream<String> rules = repository.findRulesAvailableForClient(command.getClient())) {

      response = rules
          .parallel()
          .filter(command.getUrl()::matches)
          .findFirst()
          .map(rule -> UrlValidationResponse.forMatch(command.getCorrelationId(), rule))
          .orElse(UrlValidationResponse.forUnmatch(command.getCorrelationId()));
    }

    return response;
  }
}
