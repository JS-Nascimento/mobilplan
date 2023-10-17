package br.dev.jstec.efurniture.infrastructure.persistence.marceneiro;

import br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro;
import br.dev.jstec.efurniture.application.domain.marceneiro.MarceneiroId;
import br.dev.jstec.efurniture.application.domain.valueobject.Email;
import br.dev.jstec.efurniture.application.repository.MarceneiroRepository;
import java.util.Optional;

public class MarceneiroDatabaseRepository implements MarceneiroRepository {

    @Override
    public Optional<Marceneiro> buscarPorId(MarceneiroId anId) {
        return Optional.empty();
    }

    @Override
    public Optional<Marceneiro> buscarPorEmail(Email email) {
        return Optional.empty();
    }

    @Override
    public Optional<Marceneiro> buscarPorDocumento(String documento) {
        return Optional.empty();
    }

    @Override
    public Marceneiro salvar(Marceneiro marceneiro) {
        return null;
    }
}
