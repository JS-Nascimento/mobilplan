package br.dev.jstec.mobilplan.infrastructure.rest.controller.configuracaofabricacao;

import static br.dev.jstec.mobilplan.infrastructure.configuration.security.UserContext.getUserLogged;

import br.dev.jstec.mobilplan.application.usecases.configuracaofabricacao.AlterarConfiguracaoFabricacaoUseCase;
import br.dev.jstec.mobilplan.application.usecases.configuracaofabricacao.BuscarConfiguracaoFabricacaoPorTenantUseCase;
import br.dev.jstec.mobilplan.application.usecases.configuracaofabricacao.CriarConfiguracaoFabricacaoUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.configuracaofabricacao.ConfiguracaoFabricacaoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/v1/parametros/configuracao-fabricacao")
@RequiredArgsConstructor
@Slf4j
public class ConfiguracaoFabricacaoController {

    private final CriarConfiguracaoFabricacaoUseCase criarConfiguracaoFabricacaoUseCase;
    private final AlterarConfiguracaoFabricacaoUseCase alterarConfiguracaoFabricacaoUseCase;
    private final BuscarConfiguracaoFabricacaoPorTenantUseCase buscarConfiguracaoFabricacaoPorTenantUseCase;
    private final ConfiguracaoFabricacaoDtoMapper mapper;

    @GetMapping
    public ResponseEntity<ConfiguracaoFabricacaoDto> buscarPorTenant() {

        log.info("Buscando configuração de fabricação por tenant");
        return ResponseEntity.ok(mapper.toDto(buscarConfiguracaoFabricacaoPorTenantUseCase.execute()));
    }

    @PostMapping
    public ResponseEntity<ConfiguracaoFabricacaoDto> criar(@RequestBody ConfiguracaoFabricacaoDto dto) {

        log.info("Criando configuração de fabricação");
        dto.setTenantId(getUserLogged());
        return ResponseEntity.ok(
                mapper.toDto(criarConfiguracaoFabricacaoUseCase.execute(
                        mapper.toInsertInputModel(dto))));
    }

    @PutMapping
    public ResponseEntity<ConfiguracaoFabricacaoDto> alterar(@RequestBody ConfiguracaoFabricacaoDto dto) {

        log.info("Alterando configuração de fabricação");
        dto.setTenantId(getUserLogged());
        return ResponseEntity.ok(
                mapper.toDto(alterarConfiguracaoFabricacaoUseCase.execute(
                        mapper.toUpdateInputModel(dto))));
    }
}
