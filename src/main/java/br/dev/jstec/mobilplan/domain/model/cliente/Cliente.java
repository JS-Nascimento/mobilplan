package br.dev.jstec.mobilplan.domain.model.cliente;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.enums.TipoPessoa;
import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import br.dev.jstec.mobilplan.domain.model.Tenant;
import br.dev.jstec.mobilplan.domain.valueobject.Email;
import br.dev.jstec.mobilplan.domain.valueobject.EnderecoVO;
import br.dev.jstec.mobilplan.domain.valueobject.TelefoneVO;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Cliente extends Tenant implements Serializable {


    @Include
    private UUID id;
    private final boolean ativo;
    private final String nome;
    private final TipoPessoa tipoPessoa;
    private final Email email;
    private final DadosContratuais dadosContratuais;
    private final boolean notificarPorEmail;
    private final boolean notificarPorWhatsapp;
    private Set<TelefoneVO> telefones = new HashSet<>();
    private Set<EnderecoVO> enderecos = new HashSet<>();
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    private Cliente(boolean ativo,
                    String nome,
                    TipoPessoa tipoPessoa,
                    Email email,
                    Set<TelefoneVO> telefones,
                    Set<EnderecoVO> enderecos,
                    DadosContratuais dadosContratuais,
                    boolean notificarPorEmail,
                    boolean notificarPorWhatsapp,
                    UUID tenantId) {
        super(tenantId);
        this.ativo = ativo;
        this.nome = nome;
        this.tipoPessoa = tipoPessoa;
        this.email = email;
        this.telefones = telefones == null ? Collections.emptySet() : telefones;
        this.enderecos = enderecos == null ? Collections.emptySet() : enderecos;
        this.dadosContratuais = dadosContratuais;
        this.notificarPorEmail = notificarPorEmail;
        this.notificarPorWhatsapp = notificarPorWhatsapp;

        validar();
    }

    private Cliente(UUID id,
                    boolean ativo,
                    String nome,
                    TipoPessoa tipoPessoa,
                    Email email,
                    Set<TelefoneVO> telefones,
                    Set<EnderecoVO> enderecos,
                    DadosContratuais dadosContratuais,
                    boolean notificarPorEmail,
                    boolean notificarPorWhatsapp,
                    LocalDateTime criadoEm,
                    LocalDateTime atualizadoEm,
                    UUID tenantId) {
        super(tenantId);
        this.id = id;
        this.ativo = ativo;
        this.nome = nome;
        this.tipoPessoa = tipoPessoa;
        this.email = email;
        this.dadosContratuais = dadosContratuais;
        this.notificarPorEmail = notificarPorEmail;
        this.notificarPorWhatsapp = notificarPorWhatsapp;
        this.telefones = telefones;
        this.enderecos = enderecos;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }

    public static Cliente of(boolean ativo,
                             String nome,
                             String tipoPessoa,
                             String email,
                             Set<TelefoneVO> telefones,
                             Set<EnderecoVO> enderecos,
                             DadosContratuais dadosContratuais,
                             boolean notificarPorEmail,
                             boolean notificarPorWhatsapp,
                             UUID tenantId) {

        return new Cliente(ativo,
                nome,
                TipoPessoa.of(tipoPessoa),
                Email.of(email),
                telefones,
                enderecos,
                dadosContratuais,
                notificarPorEmail,
                notificarPorWhatsapp,
                tenantId);
    }

    public static Cliente with(UUID id,
                               boolean ativo,
                               String nome,
                               String tipoPessoa,
                               String email,
                               Set<TelefoneVO> telefones,
                               Set<EnderecoVO> enderecos,
                               DadosContratuais dadosContratuais,
                               boolean notificarPorEmail,
                               boolean notificarPorWhatsapp,
                               LocalDateTime criadoEm,
                               LocalDateTime atualizadoEm,
                               UUID tenantId) {

        return new Cliente(
                id,
                ativo,
                nome,
                TipoPessoa.of(tipoPessoa),
                Email.of(email),
                telefones,
                enderecos,
                dadosContratuais,
                notificarPorEmail,
                notificarPorWhatsapp,
                criadoEm,
                atualizadoEm,
                tenantId);
    }

    private void validar() {
        if (super.getTenantId() == null || super.getTenantId().toString().isBlank()) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "TenantId");
        }
        if (nome == null || nome.isBlank()) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "Nome");
        }
    }
}
