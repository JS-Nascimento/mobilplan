package br.dev.jstec.mobilplan.infrastructure.jpa.materiaprima;

import br.dev.jstec.mobilplan.infrastructure.persistence.entity.materiaprima.PuxadorEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PuxadorRepository extends JpaRepository<PuxadorEntity, Long>,
        JpaSpecificationExecutor<PuxadorEntity> {

    Optional<PuxadorEntity> findByIdAndTenantId(Long id, UUID tenantId);

    void deleteByIdAndTenantId(Long id, UUID tenantId);

}
