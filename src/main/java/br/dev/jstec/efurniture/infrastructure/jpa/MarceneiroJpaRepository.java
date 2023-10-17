package br.dev.jstec.efurniture.infrastructure.jpa;

import br.dev.jstec.efurniture.infrastructure.persistence.marceneiro.MarceneiroEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface MarceneiroJpaRepository extends CrudRepository<MarceneiroEntity, UUID> {

}
