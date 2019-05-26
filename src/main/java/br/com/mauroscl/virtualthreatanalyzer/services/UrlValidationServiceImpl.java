package br.com.mauroscl.virtualthreatanalyzer.services;

import br.com.mauroscl.virtualthreatanalyzer.infra.WhiteListRuleRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UrlValidationServiceImpl implements UrlValidationService {

  private final WhiteListRuleRepository repository;
  private final List<String> rules;


  public UrlValidationServiceImpl(final WhiteListRuleRepository repository) {
    this.repository = repository;
    rules = repository.findAll().stream().map(WhiteListRule::getRegex).collect(Collectors.toList());
  }

  @Override
  public UrlValidationResponse validar(ValidationCommand command) {

    System.out.println(command.getCorrelationId() + " - INICIO - " +  LocalDateTime.now());
    //List<String> rules = repository.findRulesAvailableForClient(command.getClient());
    //System.out.println(command.getCorrelationId() + " - QUANT: " +  rules.size() + " - " +  LocalDateTime.now());

    final UrlValidationResponse urlValidationResponse = rules
        //.parallelStream()
        .stream()
        .filter(command.getUrl()::matches).findFirst()
        .map(rule -> UrlValidationResponse.forMatch(command.getCorrelationId(), rule))
        .orElse(UrlValidationResponse.forUnmatch(command.getCorrelationId()));

    System.out.println(command.getCorrelationId() + " - RESULTADO - " +  LocalDateTime.now());

    return urlValidationResponse;

  }
}
