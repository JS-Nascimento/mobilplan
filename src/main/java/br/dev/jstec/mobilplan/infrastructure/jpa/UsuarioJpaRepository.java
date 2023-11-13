package br.dev.jstec.mobilplan.infrastructure.jpa;

import br.dev.jstec.mobilplan.infrastructure.persistence.usuario.UsuarioEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioJpaRepository extends CrudRepository<UsuarioEntity, UUID> {

     Optional<UsuarioEntity> findByEmail(String email);
}
