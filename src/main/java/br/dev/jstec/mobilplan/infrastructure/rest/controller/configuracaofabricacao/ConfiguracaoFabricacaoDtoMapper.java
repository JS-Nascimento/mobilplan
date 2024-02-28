package br.dev.jstec.mobilplan.infrastructure.rest.controller.configuracaofabricacao;

import br.dev.jstec.mobilplan.application.usecases.configuracaofabricacao.AlterarConfiguracaoFabricacaoUseCase;
import br.dev.jstec.mobilplan.application.usecases.configuracaofabricacao.BuscarConfiguracaoFabricacaoPorTenantUseCase;
import br.dev.jstec.mobilplan.application.usecases.configuracaofabricacao.CriarConfiguracaoFabricacaoUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.configuracaofabricacao.ConfiguracaoFabricacaoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConfiguracaoFabricacaoDtoMapper {

    ConfiguracaoFabricacaoDto toDto(CriarConfiguracaoFabricacaoUseCase.Output output);

    ConfiguracaoFabricacaoDto toDto(AlterarConfiguracaoFabricacaoUseCase.Output output);

    ConfiguracaoFabricacaoDto toDto(BuscarConfiguracaoFabricacaoPorTenantUseCase.Output output);

    CriarConfiguracaoFabricacaoUseCase.Input toInsertInputModel(ConfiguracaoFabricacaoDto dto);

    AlterarConfiguracaoFabricacaoUseCase.Input toUpdateInputModel(ConfiguracaoFabricacaoDto dto);
}
