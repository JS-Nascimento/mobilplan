package br.dev.jstec.efurniture.infrastructure.persistence.marceneiro;

import static java.util.UUID.fromString;

import br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro;
import br.dev.jstec.efurniture.application.domain.marceneiro.MarceneiroId;
import br.dev.jstec.efurniture.application.domain.valueobject.Email;
import br.dev.jstec.efurniture.application.repository.MarceneiroRepository;
import br.dev.jstec.efurniture.infrastructure.jpa.MarceneiroJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MarceneiroDatabaseRepository implements MarceneiroRepository {

    private final MarceneiroJpaRepository repository;
    private final MarceneiroEntityMapper mapper;

    @Override
    public Optional<Marceneiro> buscarPorId(MarceneiroId anId) {

        return repository.findById(fromString(anId.value()))
            .map(mapper::mapToMarceneiro)
            .or(Optional::empty);
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
