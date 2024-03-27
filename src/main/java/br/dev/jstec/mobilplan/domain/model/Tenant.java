package br.dev.jstec.mobilplan.domain.model;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public abstract class Tenant {
    private final UUID tenantId;


    protected void validar() {

        if (tenantId == null || tenantId.toString().isBlank()) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "TenantId");
        }
    }
}
