package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarInteger;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarStringComLetraENumero;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarStringNumerica;
import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_TIPO_TELEFONE_INCOMPATIVEL;
import static java.text.MessageFormat.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.efurniture.application.domain.TipoTelefone;
import br.dev.jstec.efurniture.exceptions.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TelefoneTest {

    @Test
    @DisplayName("Não deve lançar exceção quando criar um telefone fixo com sucesso")
    void shouldNotThrowsExceptionWhenCreateTelefoneFixoWithSuccess() {

        assertDoesNotThrow(TelefoneFixture::buildFixo);

    }

    @Test
    @DisplayName("Não deve lançar exceção quando criar um telefone Celular ou Whatsapp com sucesso")
    void shouldNotThrowsExceptionWhenCreateCelularOuWhatsAppWithSuccess() {

        assertDoesNotThrow(TelefoneFixture::buildCelularOuWhatsapp);

    }

    @Test
    @DisplayName("Deve lançar exceção quando criar um telefone com Ddi inválido")
    void shouldThrowsExceptionWhenCreateTelefoneComDdiInvalido() {

        var ddiInvalido = gerarStringNumerica(4);
        var exception = assertThrows(BusinessException.class,
            () -> TelefoneFixture.buildDdiInvalido(ddiInvalido));

        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), "DDI"),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve lançar exceção quando criar um telefone com Ddd inválido")
    void shouldThrowsExceptionWhenCreateTelefoneComDddInvalido() {

        var dddInvalido = gerarStringNumerica(1);
        var exception = assertThrows(BusinessException.class,
            () -> TelefoneFixture.buildDddInvalido(dddInvalido));

        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), "DDD"),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve lançar exceção quando criar um telefone com Ddi nao informado")
    void shouldThrowsExceptionWhenCreateTelefoneComDdiNulo() {

        var exception = assertThrows(BusinessException.class,
            TelefoneFixture::buildDdiVazio);

        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), "DDI"),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve lançar exceção quando criar um telefone com Ddd nao informado")
    void shouldThrowsExceptionWhenCreateTelefoneComDddNulo() {

        var exception = assertThrows(BusinessException.class,
            TelefoneFixture::buildDddVazio);

        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), "DDD"),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve lançar exceção quando criar um telefone com numero nao informado")
    void shouldThrowsExceptionWhenCreateTelefoneComNumeroNulo() {

        var exception = assertThrows(BusinessException.class,
            TelefoneFixture::buildNumeroVazio);

        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), "número do telefone"),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve lançar exceção quando criar um telefone com Tipo inválido")
    void shouldThrowsExceptionWhenCreateTelefoneComTipoInvalido() {

        var exception = assertThrows(BusinessException.class,
            TelefoneFixture::buildTelefoneTipoTelefoneInvalido);

        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), "Tipo de telefone"),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o tipo Fixo for incompatível com o número")
    void shouldThrowsExceptionWhenCreateTelefoneComTipoFixoIncompativelComNumero() {

        var tipo = TipoTelefone.byOrdinal(0).getDescricao();
        var numero = gerarStringNumerica(9);

        var exception = assertThrows(BusinessException.class,
            () -> TelefoneFixture.buildNumeroTelefoneIncompativelComNumero(tipo, numero));

        assertEquals(ERRO_TIPO_TELEFONE_INCOMPATIVEL.getCode(),
            exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_TIPO_TELEFONE_INCOMPATIVEL.getMsg(),
            tipo, numero), exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o tipo Celular ou Whatsapp for incompatível com o número")
    void shouldThrowsExceptionWhenCreateTelefoneComTipoCelularOuWhatsAppIncompativelComNumero() {

        var tipo = TipoTelefone.byOrdinal(gerarInteger(1, 2)).getDescricao();
        var numero = gerarStringNumerica(8);

        var exception = assertThrows(BusinessException.class,
            () -> TelefoneFixture.buildNumeroTelefoneIncompativelComNumero(tipo, numero));

        assertEquals(ERRO_TIPO_TELEFONE_INCOMPATIVEL.getCode(),
            exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_TIPO_TELEFONE_INCOMPATIVEL.getMsg(),
            tipo, numero), exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o número for inválido")
    void shouldThrowsExceptionWhenCreateTelefoneComNumeroInvalido() {

        var tipo = TipoTelefone.byOrdinal(gerarInteger(1, 2)).getDescricao();
        var numero = gerarStringComLetraENumero(9);

        var exception = assertThrows(BusinessException.class,
            () -> TelefoneFixture.buildNumeroTelefoneIncompativelComNumero(tipo, numero));

        assertEquals(ERRO_TIPO_TELEFONE_INCOMPATIVEL.getCode(),
            exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_TIPO_TELEFONE_INCOMPATIVEL.getMsg(),
            tipo, removerNaoDigitos(numero)), exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve formatar um telefone com sucesso")
    void shouldFormatTelefoneWithSuccess() {

        var telefone = TelefoneFixture.buildFixo();
        var telefoneFormatado = TelefoneFixture.buildTelfoneFormatado(telefone);

        assertEquals(telefoneFormatado, Telefone.formatedOf(telefone));
    }

    private String removerNaoDigitos(String valor) {
        return valor.replaceAll("\\D", "");
    }
}