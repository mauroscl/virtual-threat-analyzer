package br.com.mauroscl.virtualthreatanalyzer.services;

import java.util.Random;

public class UrlValidationResponse {
  private boolean match;
  private String regex;
  private int correlationId;

  private UrlValidationResponse() {
  }

  public static UrlValidationResponse forMatch(int correlationId, String regex) {
    final UrlValidationResponse response = new UrlValidationResponse();
    response.match = true;
    response.correlationId = correlationId;
    response.regex = regex;
    return response;
  }

  public static UrlValidationResponse forUnmatch(int correlationId) {
    final UrlValidationResponse response = new UrlValidationResponse();
    response.match = false;
    response.correlationId = correlationId;
    return response;
  }


  protected UrlValidationResponse(int correlationId) {
    final Random random = new Random();

    this.match = random.nextBoolean();
    this.correlationId = correlationId;
    this.regex = generateString(30, random);
  }

  private String generateString(int tamanho, Random random) {
    final char[] chars = new char[tamanho];
    for (int i = 0 ; i < chars.length ; i++) {
      chars[i] = (char) (random.nextInt(122-97 + 1) + 97);
    }
    return new String(chars);
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
