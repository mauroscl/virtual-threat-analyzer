package br.com.mauroscl.virtualthreatanalyzer.infra;

import br.com.mauroscl.virtualthreatanalyzer.services.WhiteListRule;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

interface SpringDataRepository extends CrudRepository<WhiteListRule, Long> {
  @Query("select regex from WhiteListRule where client = ?1 or client is null")
  List<String> findByClientOrClientIsNull(String client);
}
