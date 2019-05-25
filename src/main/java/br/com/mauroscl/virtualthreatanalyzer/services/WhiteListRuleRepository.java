package br.com.mauroscl.virtualthreatanalyzer.services;

import java.util.List;

public interface WhiteListRuleRepository {
  List<String> findRulesAvailableForClient(String client);
  void save(WhiteListRule rule);
}
