package br.com.mauroscl.virtualthreatanalyzer.services;

import br.com.mauroscl.virtualthreatanalyzer.infra.WhiteListRuleRepository;
import java.time.LocalDateTime;
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

    System.out.println(command.getCorrelationId() +  " - QUERY INICIO -" + LocalDateTime.now());
    final Optional<String> mayBeRegEx = repository
        .findByClientAndUrl(command.getClient(), command.getUrl());

    System.out.println(command.getCorrelationId() +  " - QUERY FIM -" + LocalDateTime.now());

    final UrlValidationResponse response = mayBeRegEx
        .map(regex -> UrlValidationResponse.forMatch(command.getCorrelationId(), regex))
        .orElse(UrlValidationResponse.forUnmatch(command.getCorrelationId()));

    System.out.println(command.getCorrelationId() +  " - RESPOSTA -" + LocalDateTime.now());

    return response;

  }
}
