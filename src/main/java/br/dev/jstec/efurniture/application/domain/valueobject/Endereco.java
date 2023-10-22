package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS;
import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.efurniture.application.util.StringUtil.normalizarNome;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.efurniture.application.exceptions.BusinessException;

public record Endereco(
    Long id,
    String cep,
    String logradouro,
    String numero,
    String complemento,
    String bairro,
    String cidade,
    String uf) {

    public Endereco(
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf) {

        this(null, cep, logradouro, numero, complemento, bairro, cidade, uf);
    }

    public Endereco {

        if (validarDadosObrigatorios(cep)
            || validarDadosObrigatorios(logradouro)
            || validarDadosObrigatorios(numero)
            || validarDadosObrigatorios(bairro)
            || validarDadosObrigatorios(cidade)
            || validarDadosObrigatorios(uf)) {

            throw new BusinessException(ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS);
        }

        if (!validarCep(cep)) {

            throw new BusinessException(ERRO_CAMPO_INVALIDO, "CEP");
        }

        uf = uf.toUpperCase();

        if (!validarUf(uf)) {

            throw new BusinessException(ERRO_CAMPO_INVALIDO, "UF");
        }

        logradouro = normalizarNome(logradouro);
        bairro = normalizarNome(bairro);
        cidade = normalizarNome(cidade);
    }

    public static String formatedOf(Endereco endereco) {

        return format(
            "%s, Nº %s, %s, bairro: %s, %s - %s, CEP: %s,",
            endereco.logradouro(),
            endereco.numero(),
            endereco.complemento(),
            endereco.bairro(),
            endereco.cidade(),
            endereco.uf(),
            endereco.cep()
        );
    }

    public static Endereco createOf(String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf) {
        return new Endereco(cep, logradouro, numero, complemento, bairro, cidade, uf);
    }

    private static boolean validarDadosObrigatorios(String dado) {

        return isBlank(dado);
    }

    private static boolean validarCep(String cep) {

        return cep.matches("\\d{8}");
    }

    private static boolean validarUf(String uf) {

        return uf.matches("[A-Z]{2}");
    }
}
