package br.dev.jstec.mobilplan.infrastructure.jpa.materiaprima;

import br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima.MdfEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MdfRepository extends JpaRepository<MdfEntity, Long>,
        JpaSpecificationExecutor<MdfEntity> {

    Optional<MdfEntity> findByIdAndTenantId(Long id, UUID tenantId);

    void deleteByIdAndTenantId(Long id, UUID tenantId);

}
