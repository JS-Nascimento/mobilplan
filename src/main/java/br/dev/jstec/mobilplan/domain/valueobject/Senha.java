package br.dev.jstec.mobilplan.domain.valueobject;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_SENHA_INVALIDA;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Value
public class Senha {

    String value;

    private Senha(String value, boolean validar) {
        if (validar) {
            validate(value);
        }
        this.value = value;
    }

    public static Senha ofPureText(String value) {
        return new Senha(value, true);
    }

    public static Senha ofHashed(String value) {
        return new Senha(value, false);
    }

    private static void validate(String value) {

        if (isBlank(value) || value.length() < 6 || value.length() > 10
            || !value.matches(
                "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=-])(?!.*[!&*()]).{6,10}$")) {
            throw new DomainException(ERRO_SENHA_INVALIDA);
        }
    }
}
