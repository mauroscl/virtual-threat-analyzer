package br.com.mauroscl.virtualthreatanalyzer.infra;

import br.com.mauroscl.virtualthreatanalyzer.services.WhiteListRule;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface SpringDataRepository extends CrudRepository<WhiteListRule, Long> {
  List<WhiteListRule> findByClientOrClientIsNull(String client);
}
