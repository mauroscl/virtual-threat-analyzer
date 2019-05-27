package br.com.mauroscl.virtualthreatanalyzer.services;

import br.com.mauroscl.virtualthreatanalyzer.model.UrlValidationResponse;

public interface UrlValidationService {

  UrlValidationResponse validar(ValidationCommand command);
}
