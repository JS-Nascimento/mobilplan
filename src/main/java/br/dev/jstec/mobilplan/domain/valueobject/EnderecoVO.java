package br.dev.jstec.mobilplan.domain.valueobject;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.util.NumberHelper.removeNotNumbers;
import static br.dev.jstec.mobilplan.domain.util.StringUtil.normalizarNome;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = PRIVATE)
public class EnderecoVO {

    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;

    private EnderecoVO(
            String cep,
            String logradouro,
            String numero,
            String complemento,
            String bairro,
            String cidade,
            String uf
    ) {

        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;

        validar();
    }

    public static EnderecoVO with(
            String cep,
            String logradouro,
            String numero,
            String complemento,
            String bairro,
            String cidade,
            String uf
    ) {
        return new EnderecoVO(cep, logradouro, numero, complemento, bairro, cidade, uf);
    }

    private static boolean validarDadosObrigatorios(String dado) {

        return isBlank(dado);
    }

    private static boolean validarCep(String cep) {
        cep = removeNotNumbers(cep);

        return cep.matches("\\d{5,8}");
    }

    private static boolean validarUf(String uf) {

        return uf.matches("[A-Z]{2}");
    }

    private void validar() {

        if (validarDadosObrigatorios(cep)
                || validarDadosObrigatorios(logradouro)
                || validarDadosObrigatorios(numero)
                || validarDadosObrigatorios(bairro)
                || validarDadosObrigatorios(cidade)
                || validarDadosObrigatorios(uf)) {

            throw new DomainException(ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS);
        }

        if (!validarCep(cep)) {

            throw new DomainException(ERRO_CAMPO_INVALIDO, "CEP");
        }

        uf = uf.toUpperCase();

        if (!validarUf(uf)) {

            throw new DomainException(ERRO_CAMPO_INVALIDO, "UF");
        }

        logradouro = normalizarNome(logradouro);
        bairro = normalizarNome(bairro);
        cidade = normalizarNome(cidade);
    }
}
