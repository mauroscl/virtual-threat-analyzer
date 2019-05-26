package br.com.mauroscl.virtualthreatanalyzer.infra;

import br.com.mauroscl.virtualthreatanalyzer.services.WhiteListRule;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WhiteListRuleRepository extends JpaRepository<WhiteListRule, Long> {
  @Query("select regex from WhiteListRule where client = ?1 or client is null")
  List<String> findRulesAvailableForClient(String client);

  @Query(value = "select regex\n" + "from white_list_rule\n"
      + "where (client = ? or client is null)\n" + "and ? REGEXP regex\n" + "LIMIT 1", nativeQuery = true)
  Optional<String> findByClientAndUrl(String client, String url);

}
