package br.com.mauroscl.virtualthreatanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.com.mauroscl.virtualthreatanalyzer.infra")
@EnableCaching
public class VirtualThreatAnalyzerApplication {

  public static void main(String[] args) {
    SpringApplication.run(VirtualThreatAnalyzerApplication.class, args);
  }

}
