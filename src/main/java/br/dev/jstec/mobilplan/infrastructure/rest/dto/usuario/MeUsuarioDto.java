package br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario;

import br.dev.jstec.mobilplan.infrastructure.rest.dto.TelefoneDto;

public record MeUsuarioDto(
        String id,
        String nome,
        String email,
        TelefoneDto telefone
) {
}
