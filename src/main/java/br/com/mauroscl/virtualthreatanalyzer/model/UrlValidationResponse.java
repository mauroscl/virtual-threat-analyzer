package br.com.mauroscl.virtualthreatanalyzer.model;

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

  public boolean isMatch() {
    return match;
  }

  public String getRegex() {
    return regex;
  }

  public int getCorrelationId() {
    return correlationId;
  }

  @Override
  public String toString() {
    return "UrlValidationResponse{" + "match=" + match + ", regex='" + regex + '\''
        + ", correlationId=" + correlationId + '}';
  }
}
