package br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario;

import br.dev.jstec.mobilplan.infrastructure.rest.dto.TelefoneDto;

public record RequestUsuarioDto(
        String id,
        String nome,
        String email,
        TelefoneDto telefone
) {
}
