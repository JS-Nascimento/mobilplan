package br.dev.jstec.efurniture.application.domain.marceneiro;

import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_ATRIBUTO_OBRIGATORIO;
import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;
import static java.util.Objects.isNull;

import br.dev.jstec.efurniture.application.domain.valueobject.AuditInfo;
import br.dev.jstec.efurniture.application.domain.valueobject.Email;
import br.dev.jstec.efurniture.application.domain.valueobject.Endereco;
import br.dev.jstec.efurniture.application.domain.valueobject.Logomarca;
import br.dev.jstec.efurniture.application.domain.valueobject.Nome;
import br.dev.jstec.efurniture.application.domain.valueobject.NomeComercial;
import br.dev.jstec.efurniture.application.domain.valueobject.Telefone;
import br.dev.jstec.efurniture.application.domain.valueobject.TipoCliente;
import br.dev.jstec.efurniture.application.exceptions.BusinessException;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Slf4j
@ToString
@Getter
public class Marceneiro {

    private final MarceneiroId marceneiroId;
    private final TipoCliente tipoCliente;
    private final List<Telefone> telefones;
    private final List<Endereco> enderecos;
    private final AuditInfo auditInfo;
    private Nome nome;
    private NomeComercial nomeComercial;
    private Email email;
    private Logomarca logomarca;

    public Marceneiro(
        final MarceneiroId marceneiroId,
        final String nome,
        final String nomeComercial,
        final TipoCliente tipoCliente,
        final String email,
        final List<Telefone> telefones,
        final List<Endereco> enderecos,
        final AuditInfo auditInfo) {

        if (isNull(marceneiroId)) {
            throw new BusinessException(ERRO_ID_INVALIDO, "marceneiro");
        }

        if (isNull(telefones) || telefones.isEmpty()) {

            throw new BusinessException(ERRO_ATRIBUTO_OBRIGATORIO, "telefone");
        }

        if (isNull(enderecos) || enderecos.isEmpty()) {

            throw new BusinessException(ERRO_ATRIBUTO_OBRIGATORIO, "endereço");
        }

        this.marceneiroId = marceneiroId;
        this.setNome(nome);
        this.setNomeComercial(nomeComercial);
        this.tipoCliente = tipoCliente;
        this.setEmail(email);
        this.telefones = telefones;
        this.enderecos = enderecos;
        this.auditInfo = auditInfo;
    }

    public static Marceneiro createOf(
        final String nome,
        final String nomeComercial,
        final TipoCliente tipoCliente,
        final String email,
        final List<Telefone> telefones,
        final List<Endereco> enderecos,
        final String createdBy) {

        return new Marceneiro(
            MarceneiroId.unique(),
            nome,
            nomeComercial,
            tipoCliente,
            email,
            telefones,
            enderecos,
            AuditInfo.auditedCreateOf(createdBy));
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

        return new EqualsBuilder().append(marceneiroId, that.marceneiroId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(marceneiroId).toHashCode();
    }

    private void setNome(String nome) {

        this.nome = new Nome(nome);
    }

    public void setNomeComercial(String nomeComercial) {

        this.nomeComercial = new NomeComercial(nomeComercial);
    }

    public void setEmail(String email) {
        this.email = new Email(email);
    }

    public void setLogomarca(String logomarca) {

        this.logomarca = Logomarca.of(logomarca);
    }

    public MarceneiroId marceneiroId() {

        return marceneiroId;
    }

    public Nome nome() {
        return nome;
    }

    public NomeComercial nomeComercial() {
        return nomeComercial;
    }

    public TipoCliente tipoCliente() {
        return tipoCliente;
    }

    public Email email() {
        return email;
    }

    public List<Telefone> telefones() {
        return telefones;
    }

    public List<Endereco> enderecos() {
        return enderecos;
    }

    public AuditInfo auditInfo() {
        return auditInfo;
    }

    public Logomarca logomarca() {
        return logomarca;
    }
}
