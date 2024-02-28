package br.dev.jstec.mobilplan.infrastructure.jpa;

import br.dev.jstec.mobilplan.infrastructure.persistence.entity.configuracaofabricacao.ConfiguracaoFabricacaoEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoFabricacaoRepository extends JpaRepository<ConfiguracaoFabricacaoEntity, Long>,
        JpaSpecificationExecutor<ConfiguracaoFabricacaoEntity> {

    Optional<ConfiguracaoFabricacaoEntity> findByIdAndTenantId(Long id, UUID tenantId);

    Optional<ConfiguracaoFabricacaoEntity> findByTenantId(UUID tenantId);

    void deleteByIdAndTenantId(Long id, UUID tenantId);

}