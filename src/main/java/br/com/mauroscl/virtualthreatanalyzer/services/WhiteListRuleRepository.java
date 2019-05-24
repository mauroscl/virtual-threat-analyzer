package br.com.mauroscl.virtualthreatanalyzer.services;

import java.util.List;

public interface WhiteListRuleRepository {
  List<WhiteListRule> findRulesAvailableForClient(String client);
  void save(WhiteListRule rule);
}
