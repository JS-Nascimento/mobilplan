package br.dev.jstec.mobilplan.application.usecases.usuario;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_CONVERTER_IMAGEM;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_SALVAR_IMAGEM;
import static java.util.UUID.fromString;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.UsuarioPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.application.usecases.usuario.SalvarAvatarUseCase.Input;
import br.dev.jstec.mobilplan.application.usecases.usuario.SalvarAvatarUseCase.Output;
import br.dev.jstec.mobilplan.domain.valueobject.Imagem;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

@RequiredArgsConstructor
public class SalvarAvatarUseCase extends UseCase<Input, Output> {

    private final UsuarioPort usuarioPort;
    private static final Set<String> SUPPORTED_CONTENT_TYPES = Set.of(
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_JPEG_VALUE
    );


    @Override
    public Output execute(SalvarAvatarUseCase.Input input) {

        if (!SUPPORTED_CONTENT_TYPES.contains(input.tipoImagem())) {
            throw new BusinessException(ERRO_CONVERTER_IMAGEM);
        }

        var avatar = Imagem.of("avatar", input.inputStream());

        return usuarioPort.buscarPorId(fromString(input.id()))
                .map(usuario -> {
                    try {
                        return new Output(
                                usuarioPort.salvarAvatar(
                                        usuario,
                                        avatar.fileName(),
                                        input.tipoImagem(),
                                        avatar.image()
                                )
                        );
                    } catch (IOException e) {

                        throw new BusinessException(ERRO_CONVERTER_IMAGEM);

                    } catch (URISyntaxException e) {

                        throw new BusinessException(ERRO_SALVAR_IMAGEM);
                    }
                })
                .orElseThrow(() -> new BusinessException(ERRO_ID_INVALIDO, input.id()));
    }

    public record Input(
            String id,
            String tipoImagem,
            InputStream inputStream) {

    }

    public record Output(String url) {

    }
}
