package br.com.mauroscl.virtualthreatanalyzer.services;

import br.com.mauroscl.virtualthreatanalyzer.infra.WhiteListRuleRepository;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
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

    if (!validMessage(command)) {
      throw new AmqpRejectAndDontRequeueException("mensagem mal formatada");
    }

    return findRule(() -> repository.findRulesAvailableForClient(command.getClient()), command)
        .orElseGet(() -> findRule(() -> repository.findGlobalRules(), command)
            .orElseGet(() -> UrlValidationResponse.forUnmatch(command.getCorrelationId())));

  }

  private Optional<UrlValidationResponse> findRule(Supplier<List<String>> supplier,
      ValidationCommand command) {
    return supplier.get()
        .parallelStream()
        .filter(command.getUrl()::matches).findFirst()
        .map(rule -> UrlValidationResponse.forMatch(command.getCorrelationId(), rule));
  }

  boolean validMessage(ValidationCommand command) {
    return command.getUrl() != null && command.getClient() != null;
  }

}
