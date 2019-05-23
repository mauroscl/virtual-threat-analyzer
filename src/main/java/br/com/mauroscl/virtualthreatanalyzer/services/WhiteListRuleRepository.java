package br.com.mauroscl.virtualthreatanalyzer.services;

import java.util.List;

public interface WhiteListRuleRepository {
  List<WhiteListRule> findRulesAvailableForClient(String client);
  List<WhiteListRule> findGlobalRules();
  void save(WhiteListRule rule);
}
