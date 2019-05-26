package br.com.mauroscl.virtualthreatanalyzer.infra;

import br.com.mauroscl.virtualthreatanalyzer.services.WhiteListRule;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WhiteListRuleRepository extends JpaRepository<WhiteListRule, Long> {
  @Query("select regex from WhiteListRule where client = ?1")
  @Cacheable(value = "clientRules", key = "#client")
  List<String> findRulesAvailableForClient(String client);

  @Query("select regex from WhiteListRule where client is null")
  @Cacheable(value = "globalRules")
  List<String> findGlobalRules();

  @CacheEvict(value = "clientRules", key = "#p0.getClient()")
  default WhiteListRule saveClientRule(WhiteListRule whiteListRule) {
    return this.save(whiteListRule);
  }

  @CacheEvict(value = "globalRules", allEntries = true)
  default WhiteListRule saveGlobalRule(WhiteListRule whiteListRule){
    return this.save(whiteListRule);
  }

}
