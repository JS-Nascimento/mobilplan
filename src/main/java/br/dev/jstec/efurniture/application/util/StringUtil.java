package br.dev.jstec.efurniture.application.util;

import static java.text.Normalizer.Form.NFD;
import static java.text.Normalizer.normalize;
import static java.util.stream.Stream.of;
import static lombok.AccessLevel.PRIVATE;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class StringUtil {
    public static String normalizarNome(String nome) {
        return of(nome.trim().split("\\s+"))
                .map(palavra -> palavra.contains("-")
                        ? normalizarComHifen(palavra)
                        : normalizarPalavra(palavra))
                .collect(Collectors.joining(" "));
    }

    private static String normalizarPalavra(String palavra) {
        return palavra.length() > 2
                ? palavra.substring(0, 1).toUpperCase() + palavra.substring(1).toLowerCase()
                : palavra.toLowerCase();
    }

    private static String normalizarComHifen(String palavra) {
        return of(palavra.split("-"))
                .map(StringUtil::normalizarPalavra)
                .collect(Collectors.joining("-"));
    }

    private static String normalizeString(String str) {

        var normalized = normalize(str, NFD);
        var pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

        return pattern.matcher(normalized).replaceAll("");
    }
}
