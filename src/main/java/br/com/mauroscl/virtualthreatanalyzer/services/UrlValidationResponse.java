package br.com.mauroscl.virtualthreatanalyzer.services;

import java.util.Random;

public class UrlValidationResponse {
  private boolean match;
  private String regex;
  private int correlationId;

  protected UrlValidationResponse() {
  }

  protected UrlValidationResponse(int correlationId) {
    final Random random = new Random();

    this.match = random.nextBoolean();
    this.correlationId = correlationId;
    byte[] bytesForString = new byte[]{};
    random.nextBytes(bytesForString);
    this.regex = new String(bytesForString);
  }

  public boolean isMatch() {
    return match;
  }

  protected void setMatch(final boolean match) {
    this.match = match;
  }

  public String getRegex() {
    return regex;
  }

  protected void setRegex(final String regex) {
    this.regex = regex;
  }

  public int getCorrelationId() {
    return correlationId;
  }

  protected void setCorrelationId(final int correlationId) {
    this.correlationId = correlationId;
  }
}
