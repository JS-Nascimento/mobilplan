package br.dev.jstec.mobilplan.infrastructure.jpa;

import br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima.FitaDeBordaEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FitaDeBordaRepository extends JpaRepository<FitaDeBordaEntity, Long>,
        JpaSpecificationExecutor<FitaDeBordaEntity> {

    Optional<FitaDeBordaEntity> findByIdAndTenantId(Long id, UUID tenantId);

    void deleteByIdAndTenantId(Long id, UUID tenantId);

}
