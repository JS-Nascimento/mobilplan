package br.dev.jstec.mobilplan.infrastructure.jpa;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoPadraoRepository extends JpaRepository<ConfiguracaoPadraoRepository, Long>,
        JpaSpecificationExecutor<ConfiguracaoPadraoRepository> {

    Optional<ConfiguracaoPadraoRepository> findByIdAndTenantId(Long id, UUID tenantId);

    void deleteByIdAndTenantId(Long id, UUID tenantId);

}