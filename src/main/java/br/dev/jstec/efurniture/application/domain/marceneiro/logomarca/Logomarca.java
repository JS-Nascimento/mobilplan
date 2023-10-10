package br.dev.jstec.efurniture.application.domain.marceneiro.logomarca;

import jakarta.validation.constraints.NotBlank;

public record Logomarca (
    @NotBlank String fileName,
    @NotBlank String url) {

    public Logomarca(String fileName) {
       this(fileName.toLowerCase(),
        fileName.toLowerCase());
    }

    public static Logomarca createOf(String fileName) {
        return new Logomarca(fileName);
    }
}
