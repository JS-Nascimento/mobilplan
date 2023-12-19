package br.dev.jstec.mobilplan.infrastructure.rest.dto.marceneiro;

import br.dev.jstec.mobilplan.infrastructure.rest.dto.TelefoneDto;
import java.util.List;

public record NewMarceneiroDto(String nome,
                               String nomeComercial,
                               String tipoPessoa,
                               String documento,
                               String email,
                               List<TelefoneDto> telefones,
                               List<EnderecoDto> enderecos) {

}
