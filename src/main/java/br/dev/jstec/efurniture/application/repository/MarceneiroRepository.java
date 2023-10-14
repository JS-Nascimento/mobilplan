package br.dev.jstec.efurniture.application.repository;

import br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro;
import br.dev.jstec.efurniture.application.domain.marceneiro.MarceneiroId;
import br.dev.jstec.efurniture.application.domain.valueobject.Email;
import java.util.Optional;

public interface MarceneiroRepository {

    Optional<Marceneiro> buscarPorId(MarceneiroId anId);

    Optional<Marceneiro> buscarPorEmail(Email email);

    Optional<Marceneiro> buscarPorDocumento(String documento);

    Marceneiro salvar(Marceneiro marceneiro);
}
