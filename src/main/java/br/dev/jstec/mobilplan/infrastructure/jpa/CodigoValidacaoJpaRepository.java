package br.dev.jstec.mobilplan.infrastructure.jpa;

import br.dev.jstec.mobilplan.infrastructure.persistence.usuario.CodigoValidacaoEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface CodigoValidacaoJpaRepository extends CrudRepository<CodigoValidacaoEntity, Long> {

    @Query("SELECT c FROM CodigoValidacaoEntity c WHERE c.usuario.id = :usuarioId")
    Optional<CodigoValidacaoEntity> buscarPorUsuarioId(@Param("usuarioId") UUID usuarioId);


}
