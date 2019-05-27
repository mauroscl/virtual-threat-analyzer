package br.com.mauroscl.virtualthreatanalyzer.services;

import br.com.mauroscl.virtualthreatanalyzer.infra.WhiteListRuleRepository;
import br.com.mauroscl.virtualthreatanalyzer.model.WhiteListRule;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Service;

@Service
public class WhiteListRuleServiceImpl implements WhiteListRuleService {

  private static final int  MAX_STRING_LENGTH = 128;

  private final WhiteListRuleRepository repository;

  public WhiteListRuleServiceImpl(final WhiteListRuleRepository repository) {
    this.repository = repository;
  }

  @Override
  public void createNew(WhiteListRule whiteListRule) {
    if (!isValidMessage(whiteListRule)) {
      throw new AmqpRejectAndDontRequeueException("invalid insertion message");
    }
    if (whiteListRule.getClient() == null) {
      repository.saveGlobalRule(whiteListRule);
    } else{
      repository.saveClientRule(whiteListRule);
    }

  }

  boolean isValidMessage(WhiteListRule whiteListRule) {

    return (whiteListRule.getClient() == null || whiteListRule.getClient().length() <= MAX_STRING_LENGTH)
        && whiteListRule.getRegex() != null
        && whiteListRule.getRegex().length() < MAX_STRING_LENGTH;
  }


}
