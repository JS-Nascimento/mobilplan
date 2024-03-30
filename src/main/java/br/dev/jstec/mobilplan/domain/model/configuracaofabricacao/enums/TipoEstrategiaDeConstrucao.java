package br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_ENTIDADE_INEXISTENTE;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import lombok.Getter;

@Getter
public enum TipoEstrategiaDeConstrucao {

    BASE_SOBRE_LATERAL,
    BASE_ENTRE_LATERAIS;

    public static TipoEstrategiaDeConstrucao of(String descricao) {

        if (isNull(descricao)) {

            throw new DomainException(ERRO_CAMPO_INVALIDO, "Padrao de fitagem");
        }

        return stream(TipoEstrategiaDeConstrucao.values())
                .filter(tipo -> descricao.equalsIgnoreCase(tipo.name()))
                .findFirst()
                .orElseThrow(
                        () -> new DomainException(ERRO_ENTIDADE_INEXISTENTE, "Estrátegia de Construção", descricao));
    }

    public static boolean validate(String descricao) {
        try {
            of(descricao);
            return true;
        } catch (DomainException e) {
            return false;
        }
    }
}