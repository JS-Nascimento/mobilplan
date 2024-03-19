package br.dev.jstec.mobilplan.domain.model.materiaprima;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_MENOR_IGUAL_ZERO;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
public abstract class CommonAttributes {

    private final String descricao;
    private final String cor;
    private final Unidade unidade;
    private final double preco;
    private final TipoPrecificacao precificacao;
    private final String imagem;
    private final UUID tenantId;
    private Long id;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    protected void validar() {
        if (tenantId == null || tenantId.toString().isBlank()) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "TenantId");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "Descrição");
        }
        if (cor == null || cor.isBlank()) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "Cor");
        }
        if (unidade == null) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "Unidade");
        }
        if (preco <= 0) {
            throw new DomainException(ERRO_CAMPO_MENOR_IGUAL_ZERO, "Preço");
        }
    }
}
