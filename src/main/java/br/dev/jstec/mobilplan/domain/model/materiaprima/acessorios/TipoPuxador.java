package br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios;

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
public enum TipoPuxador {

    SEM_PUXADOR("Sem Puxador"),
    PUXADOR_ALCA("Puxador Alça"),
    PUXADOR_USINADO("Puxador Usinado"),
    PUXADOR_CAVA("Puxador Cava"),
    PUXADOR_PERFIL("Puxador Perfil");

    private final String descricao;

    public static TipoPuxador of(String descricao) {
        if (isNull(descricao)) {

            throw new DomainException(ERRO_CAMPO_INVALIDO, "Tipo de Puxador");
        }

        return stream(TipoPuxador.values())
                .filter(tipo -> descricao.equals(tipo.descricao) || descricao.equals(tipo.name()))
                .findFirst()
                .orElseThrow(() -> new DomainException(ERRO_ENTIDADE_INEXISTENTE, "Tipo de Puxador"));
    }
}
