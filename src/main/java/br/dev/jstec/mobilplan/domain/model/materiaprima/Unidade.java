package br.dev.jstec.mobilplan.domain.model.materiaprima;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_ENTIDADE_INEXISTENTE;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = PRIVATE)
public enum Unidade {

    METRO_QUADRADO("m²"),
    METRO_LINEAR("m/l"),
    UNIDADE("un"),
    PAR("par"),
    JOGO("jogo"),
    PACOTE("pacote"),
    PECA("peça");

    private final String descricao;

    public static Unidade of(String descricao) {

        if (isNull(descricao)) {

            throw new DomainException(ERRO_CAMPO_INVALIDO, "Unidade");
        }

        return stream(Unidade.values())
                .filter(tipo -> descricao.equals(tipo.descricao) || descricao.equals(tipo.name()))
                .findFirst()
                .orElseThrow(() -> new DomainException(ERRO_ENTIDADE_INEXISTENTE, "Unidade"));
    }
}
