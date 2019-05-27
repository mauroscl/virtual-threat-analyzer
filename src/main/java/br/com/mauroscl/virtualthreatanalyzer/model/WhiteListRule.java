package br.com.mauroscl.virtualthreatanalyzer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WhiteListRule {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  private String client;

  private String regex;

  public Long getId() {
    return id;
  }

  public String getClient() {
    return client;
  }

  public String getRegex() {
    return regex;
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
