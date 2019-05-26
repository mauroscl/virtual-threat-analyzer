package br.com.mauroscl.virtualthreatanalyzer.infra;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

import br.com.mauroscl.virtualthreatanalyzer.services.WhiteListRule;
import java.util.stream.Stream;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

public interface WhiteListRuleRepository extends JpaRepository<WhiteListRule, Long> {

  @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
  @Query("select regex from WhiteListRule where client = ?1 or client is null")
  Stream<String> findRulesAvailableForClient(String client);
}
