package br.com.mauroscl.virtualthreatanalyzer.services;

import java.util.Random;

public class UrlValidationResponse {
  private boolean match;
  private String regex;
  private int correlationId;

  protected UrlValidationResponse() {
  }

  protected UrlValidationResponse(String regex) {
    final Random random = new Random();

    this.match = random.nextBoolean();
    this.correlationId = random.nextInt();

    this.regex = regex;
  }

  protected boolean isMatch() {
    return match;
  }

  protected void setMatch(final boolean match) {
    this.match = match;
  }

  protected String getRegex() {
    return regex;
  }

  protected void setRegex(final String regex) {
    this.regex = regex;
  }

  protected int getCorrelationId() {
    return correlationId;
  }

  protected void setCorrelationId(final int correlationId) {
    this.correlationId = correlationId;
  }
}
