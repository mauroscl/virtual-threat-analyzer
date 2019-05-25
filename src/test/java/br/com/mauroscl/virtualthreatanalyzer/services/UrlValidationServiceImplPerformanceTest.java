package br.com.mauroscl.virtualthreatanalyzer.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class UrlValidationServiceImplPerformanceTest {

  @Test
  public void verificarTempo() {
    final int[] asciis = IntStream.range(97, 123).toArray();

    final ArrayList<String> expressions = new ArrayList<>();

    for (int i = 0; i < asciis.length; i++) {
      for (int j = 0; j < asciis.length; j++) {
        for (int k = 0; k < asciis.length; k++) {
          for (int l = 0; l < asciis.length; l++) {

            expressions.add("[a-z]+\\." + new String(
                new char[]{(char) asciis[i], (char) asciis[j], (char) asciis[k], (char) asciis[l]})
                + "\\.[a-z]+");

          }

        }

      }

    }

    String search = "www.zzzz.com";

    final String result = expressions
        .parallelStream()
        .filter(search::matches)
        .findFirst()
        .orElse("nao encontrado");

    assertThat(result).isEqualTo("[a-z]+\\.zzzz\\.[a-z]+");

  }

}
