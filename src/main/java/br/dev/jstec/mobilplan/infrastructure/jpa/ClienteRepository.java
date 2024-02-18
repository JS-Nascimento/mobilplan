package br.dev.jstec.mobilplan.infrastructure.jpa;

import br.dev.jstec.mobilplan.infrastructure.persistence.entity.cliente.ClienteEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID>,
        JpaSpecificationExecutor<ClienteEntity> {

    Optional<ClienteEntity> findByIdAndTenantId(UUID id, UUID tenantId);

    void deleteByIdAndTenantId(UUID id, UUID tenantId);

}