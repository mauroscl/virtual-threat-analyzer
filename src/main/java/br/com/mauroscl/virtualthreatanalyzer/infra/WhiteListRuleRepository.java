package br.com.mauroscl.virtualthreatanalyzer.infra;

import br.com.mauroscl.virtualthreatanalyzer.services.WhiteListRule;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WhiteListRuleRepository extends JpaRepository<WhiteListRule, Long> {
  @Query("select w from WhiteListRule w where w.client = ?1")
  @Cacheable(value = "clientRules", key = "#client")
  List<WhiteListRule> findRulesAvailableForClient(String client);

  @Query("select w from WhiteListRule w where w.client is null")
  @Cacheable(value = "globalRules")
  List<WhiteListRule> findGlobalRules();
}
