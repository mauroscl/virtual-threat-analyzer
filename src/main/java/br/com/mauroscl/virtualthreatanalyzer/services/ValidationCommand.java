package br.com.mauroscl.virtualthreatanalyzer.services;

public class ValidationCommand {

  private String client;
  private String url;
  private int correlationId;

  protected String getClient() {
    return client;
  }

  protected void setClient(final String client) {
    this.client = client;
  }

  protected String getUrl() {
    return url;
  }

  protected void setUrl(final String url) {
    this.url = url;
  }

  protected int getCorrelationId() {
    return correlationId;
  }

  protected void setCorrelationId(final int correlationId) {
    this.correlationId = correlationId;
  }

  @Override
  public String toString() {
    return "ValidationCommand{" + "client='" + client + '\'' + ", url='" + url + '\''
        + ", correlationId=" + correlationId + '}';
  }
}
