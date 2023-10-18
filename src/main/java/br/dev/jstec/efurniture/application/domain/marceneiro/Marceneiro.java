package br.dev.jstec.efurniture.application.domain.marceneiro;

import static br.dev.jstec.efurniture.application.domain.marceneiro.Situacao.of;
import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_ATRIBUTO_OBRIGATORIO;
import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;

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
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
@Setter
public class Marceneiro {

    private final MarceneiroId marceneiroId;
    private final Nome nome;
    private final NomeComercial nomeComercial;
    private final Email email;
    private Situacao situacao;
    private final TipoCliente tipoCliente;
    private final List<Telefone> telefones;
    private final List<Endereco> enderecos;
    private final AuditInfo auditInfo;
    private Logomarca logomarca;

    public Marceneiro(
        MarceneiroId marceneiroId,
        Situacao situacao,
        TipoCliente tipoCliente,
        List<Telefone> telefones,
        List<Endereco> enderecos,
        Nome nome,
        NomeComercial nomeComercial,
        Email email,
        AuditInfo auditInfo) {

        validate(marceneiroId, telefones, enderecos);

        this.marceneiroId = marceneiroId;
        this.situacao = situacao;
        this.tipoCliente = tipoCliente;
        this.telefones = telefones;
        this.enderecos = enderecos;
        this.nome = nome;
        this.nomeComercial = nomeComercial;
        this.email = email;
        this.auditInfo = auditInfo;
    }

    public static Marceneiro createOf(
        String nome,
        String nomeComercial,
        TipoCliente tipoCliente,
        String email,
        List<Telefone> telefones,
        List<Endereco> enderecos) {

        return new Marceneiro(
            MarceneiroId.unique(),
            Situacao.ATIVO,
            tipoCliente,
            telefones,
            enderecos,
            new Nome(nome),
            new NomeComercial(nomeComercial),
            new Email(email),
            null);
    }

    public static Marceneiro updateStatus(Marceneiro marceneiro, String situacao) {
        marceneiro.situacao = of(situacao);
        return marceneiro;
    }

    private void validate(
        MarceneiroId marceneiroId,
        List<Telefone> telefones,
        List<Endereco> enderecos

    ) {
        if (marceneiroId == null) {
            throw new BusinessException(ERRO_ID_INVALIDO, "marceneiro");
        }

        if (telefones == null || telefones.isEmpty()) {
            throw new BusinessException(ERRO_ATRIBUTO_OBRIGATORIO, "telefone");
        }

        if (enderecos == null || enderecos.isEmpty()) {
            throw new BusinessException(ERRO_ATRIBUTO_OBRIGATORIO, "endereço");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Marceneiro that = (Marceneiro) o;
        return marceneiroId.equals(that.marceneiroId);
    }

    @Override
    public int hashCode() {
        return marceneiroId.hashCode();
    }
}
