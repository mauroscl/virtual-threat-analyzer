package br.com.mauroscl.virtualthreatanalyzer.services;

public interface UrlValidationService {

  UrlValidationResponse validar(ValidationCommand command);
}
