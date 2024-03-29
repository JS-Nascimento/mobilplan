package br.dev.jstec.mobilplan.domain.model.marceneiro;


import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_ATRIBUTO_OBRIGATORIO;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import br.dev.jstec.mobilplan.domain.valueobject.Email;
import br.dev.jstec.mobilplan.domain.valueobject.Endereco;
import br.dev.jstec.mobilplan.domain.valueobject.Nome;
import br.dev.jstec.mobilplan.domain.valueobject.NomeComercial;
import br.dev.jstec.mobilplan.domain.valueobject.Telefone;
import br.dev.jstec.mobilplan.domain.valueobject.TipoCliente;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
@Setter
public class Marceneiro {

    private final UUID id;
    private final Nome nome;
    private final NomeComercial nomeComercial;
    private final Email email;
    private Situacao situacao;
    private final TipoCliente tipoCliente;
    private final List<Telefone> telefones;
    private final List<Endereco> enderecos;
    private String logomarcaFilename;
    private String logomarcaUrl;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private UUID updatedBy;
    private LocalDateTime updatedAt;

    public Marceneiro(
            UUID id,
            Situacao situacao,
            TipoCliente tipoCliente,
            List<Telefone> telefones,
            List<Endereco> enderecos,
            Nome nome,
            NomeComercial nomeComercial,
            Email email,
            UUID createdBy,
            LocalDateTime createdAt,
            UUID updatedBy,
            LocalDateTime updatedAt) {

        validate(telefones, enderecos);

        this.id = id;
        this.situacao = situacao;
        this.tipoCliente = tipoCliente;
        this.telefones = telefones;
        this.enderecos = enderecos;
        this.nome = nome;
        this.nomeComercial = nomeComercial;
        this.email = email;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    Marceneiro(
            UUID id,
            Situacao situacao,
            TipoCliente tipoCliente,
            List<Telefone> telefones,
            List<Endereco> enderecos,
            Nome nome,
            NomeComercial nomeComercial,
            Email email) {

        validate(telefones, enderecos);

        this.id = id;
        this.situacao = situacao;
        this.tipoCliente = tipoCliente;
        this.telefones = telefones;
        this.enderecos = enderecos;
        this.nome = nome;
        this.nomeComercial = nomeComercial;
        this.email = email;
    }

    public static Marceneiro createOf(
            String nome,
            String nomeComercial,
            TipoCliente tipoCliente,
            String email,
            List<Telefone> telefones,
            List<Endereco> enderecos) {

        return new Marceneiro(
                null,
                Situacao.ATIVO,
                tipoCliente,
                telefones,
                enderecos,
                new Nome(nome),
                new NomeComercial(nomeComercial),
                new Email(email),
                null,
                null,
                null,
                null);
    }

    public static Marceneiro updateStatus(Marceneiro marceneiro, String situacao) {
        marceneiro.situacao = Situacao.of(situacao.toUpperCase());
        return marceneiro;
    }

    public static Marceneiro updateOf(
            String id,
            String situacao,
            String nome,
            String nomeComercial,
            String tipoPessoa,
            String documento,
            String email,
            List<Telefone> telefones,
            List<Endereco> enderecos) {

        return new Marceneiro(
                UUID.fromString(id),
                Situacao.of(situacao.toUpperCase()),
                TipoCliente.createOf(tipoPessoa, documento),
                telefones,
                enderecos,
                new Nome(nome),
                new NomeComercial(nomeComercial),
                new Email(email));
    }

    void validate(
            List<Telefone> telefones,
            List<Endereco> enderecos

    ) {

        if (telefones == null || telefones.isEmpty()) {
            throw new DomainException(ERRO_ATRIBUTO_OBRIGATORIO, "telefone");
        }

        if (enderecos == null || enderecos.isEmpty()) {
            throw new DomainException(ERRO_ATRIBUTO_OBRIGATORIO, "endereço");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Marceneiro that = (Marceneiro) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
