package br.dev.jstec.mobilplan.infrastructure.jpa.materiaprima;

import br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima.FerragemEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FerragemRepository extends JpaRepository<FerragemEntity, Long>,
        JpaSpecificationExecutor<FerragemEntity> {

    Optional<FerragemEntity> findByIdAndTenantId(Long id, UUID tenantId);

    void deleteByIdAndTenantId(Long id, UUID tenantId);

}
