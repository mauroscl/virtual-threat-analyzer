package br.com.mauroscl.virtualthreatanalyzer.services;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WhiteListRule {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  private String client;

  private String regex;

  public Long getId() {
    return id;
  }

  public void setClient(final String client) {
    this.client = client;
  }

  public void setRegex(final String regex) {
    this.regex = regex;
  }

  @Override
  public String toString() {
    return "WhiteListRule{" + "client='" + client + '\'' + ", regex='" + regex + '\'' + '}';
  }
}
