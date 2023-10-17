package br.dev.jstec.efurniture.infrastructure.jpa;

import br.dev.jstec.efurniture.infrastructure.persistence.marceneiro.MarceneiroEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface MarceneiroJpaRepository extends CrudRepository<MarceneiroEntity, UUID> {

    Optional<MarceneiroEntity> findByEmail(String email);

    Optional<MarceneiroEntity> findByDocumento(String documento);
}
