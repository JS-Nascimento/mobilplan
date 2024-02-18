package br.dev.jstec.mobilplan.infrastructure.gateways;

import static br.dev.jstec.mobilplan.infrastructure.exceptions.ErroTecnico.ERRO_CODIGO_VALIDACAO_EXPIRADO;
import static br.dev.jstec.mobilplan.infrastructure.exceptions.ErroTecnico.ERRO_CODIGO_VALIDACAO_INVALIDO;
import static br.dev.jstec.mobilplan.infrastructure.exceptions.ErroTecnico.ERRO_USUARIO_INEXISTENTE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import br.dev.jstec.mobilplan.infrastructure.exceptions.RequestException;
import br.dev.jstec.mobilplan.infrastructure.jpa.CodigoValidacaoJpaRepository;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.usuario.UsuarioPersistence;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario.ResponseUsuarioDto;
import java.time.LocalDateTime;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioGateway {

    private final UsuarioPersistence usuarioPersistence;
    private final CodigoValidacaoJpaRepository codigoValidacaoJpaRepository;

    public ResponseUsuarioDto validarCodigo(String codigo, String email) {

        var usuario = usuarioPersistence.buscarEntidadePorEmail(email);

        usuario.ifPresentOrElse(
            usuarioEntity -> {
                var codigoValidacaoEntity =
                    codigoValidacaoJpaRepository.buscarPorUsuarioId(usuarioEntity.getId());

                codigoValidacaoEntity.ifPresentOrElse(
                    c -> {
                        isValidTimeToCode.accept(c.getValidoAte());
                        isValidCode.accept(c.getCodigo(), codigo);
                        codigoValidacaoJpaRepository.delete(c);
                    },
                    () -> {
                        throw new RequestException(BAD_REQUEST, ERRO_CODIGO_VALIDACAO_INVALIDO,
                            UsuarioGateway.class.getSimpleName());
                    }
                );
            },
            () -> {
                throw new RequestException(BAD_REQUEST, ERRO_USUARIO_INEXISTENTE,
                    UsuarioGateway.class.getSimpleName());
            }
        );

        return  usuarioPersistence.atualizandoStatusUsuarioAposConfirmacaoEmail(usuario);
    }

    private final Consumer<LocalDateTime> isValidTimeToCode = validTo -> {
        if (LocalDateTime.now().isAfter(validTo)) {
            throw new RequestException(BAD_REQUEST, ERRO_CODIGO_VALIDACAO_EXPIRADO,
                UsuarioGateway.class.getSimpleName());
        }
    };
    private final BiConsumer<String, String> isValidCode = (codeValid, codeSent) -> {
        if (!codeSent.equals(codeValid)) {
            throw new RequestException(BAD_REQUEST, ERRO_CODIGO_VALIDACAO_INVALIDO,
                UsuarioGateway.class.getSimpleName());
        }
    };
}