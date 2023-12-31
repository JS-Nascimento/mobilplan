package br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario;

import br.dev.jstec.mobilplan.infrastructure.rest.dto.TelefoneDto;

public record UpdateUsuarioDto(
        String id,
        String nome,
        String email,
        TelefoneDto telefone,
        String avatarFilename,
        String avatarUrl) {
}
