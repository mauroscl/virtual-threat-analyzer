package br.com.mauroscl.virtualthreatanalyzer.services;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@ToString
//@Setter
//@Getter
public class WhiteListRule {
  private String client;
  private String regex;

  protected void setClient(final String client) {
    this.client = client;
  }

  protected void setRegex(final String regex) {
    this.regex = regex;
  }

  protected String getRegex() {
    return regex;
  }

  @Override
  public String toString() {
    return "WhiteListRule{" + "client='" + client + '\'' + ", regex='" + regex + '\'' + '}';
  }
}
