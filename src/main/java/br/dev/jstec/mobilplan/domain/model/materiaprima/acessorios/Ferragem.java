package br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios;

import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.model.materiaprima.CommonAttributes;
import br.dev.jstec.mobilplan.domain.model.materiaprima.TipoPrecificacao;
import br.dev.jstec.mobilplan.domain.model.materiaprima.Unidade;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class Ferragem extends CommonAttributes implements Acessorio {

    private Ferragem(String descricao, String cor, Unidade unidade, double preco,
                     TipoPrecificacao precificacao, String imagem, UUID tenantId) {
        super(descricao, cor, unidade, preco, precificacao, imagem, tenantId);
        validar();
    }

    private Ferragem(Long id, String descricao, String cor, Unidade unidade, double preco, UUID tenantId,
                     TipoPrecificacao precificacao, String imagem,
                     LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        super(descricao,
                cor,
                unidade,
                preco,
                precificacao,
                imagem,
                tenantId,
                id,
                criadoEm,
                atualizadoEm);
        validar();
    }

    public static Ferragem of(String descricao,
                              String cor,
                              String unidade,
                              double preco,
                              String precificacao,
                              String imagem,
                              UUID tenantId) {
        return new Ferragem(descricao,
                cor,
                Unidade.of(unidade),
                preco,
                TipoPrecificacao.of(precificacao),
                imagem,
                tenantId);
    }

    public static Ferragem with(Long id,
                                String descricao,
                                String cor,
                                String unidade,
                                double preco,
                                String precificacao,
                                String imagem,
                                UUID tenantId,
                                LocalDateTime criadoEm,
                                LocalDateTime atualizadoEm) {
        return new Ferragem(id,
                descricao,
                cor,
                Unidade.of(unidade),
                preco,
                tenantId,
                TipoPrecificacao.of(precificacao),
                imagem,
                criadoEm,
                atualizadoEm);
    }

    public static boolean validateCsvImport(String descricao, String cor, String unidade,
                                            double preco, String precificacao) {


        try {

            if (descricao.isBlank() || cor.isBlank() || unidade.isBlank() || precificacao.isBlank()) {
                return false;
            }

            if (preco <= 0) {
                return false;
            }

            if (!Unidade.validate(unidade)) {
                return false;
            }

            if (!TipoPrecificacao.validate(precificacao)) {
                return false;
            }

        } catch (
                Exception e) {

            return false;

        }

        return true;
    }
}
