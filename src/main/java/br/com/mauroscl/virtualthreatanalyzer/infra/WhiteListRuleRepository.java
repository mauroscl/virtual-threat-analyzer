package br.com.mauroscl.virtualthreatanalyzer.infra;

import br.com.mauroscl.virtualthreatanalyzer.services.WhiteListRule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WhiteListRuleRepository extends JpaRepository<WhiteListRule, Long> {
  @Query("select regex from WhiteListRule where client = ?1 or client is null")
  List<String> findRulesAvailableForClient(String client);
}
