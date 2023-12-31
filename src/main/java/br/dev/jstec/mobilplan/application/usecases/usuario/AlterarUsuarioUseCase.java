package br.dev.jstec.mobilplan.application.usecases.usuario;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_EMAIL_NAO_CONFIRMADO;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;
import static br.dev.jstec.mobilplan.application.usecases.usuario.AlterarUsuarioUseCase.Input;
import static br.dev.jstec.mobilplan.application.usecases.usuario.AlterarUsuarioUseCase.Output;
import static br.dev.jstec.mobilplan.domain.usuario.Usuario.with;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.UsuarioPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.usuario.UserEmailConfirmation;
import br.dev.jstec.mobilplan.domain.usuario.Usuario;
import br.dev.jstec.mobilplan.domain.valueobject.Telefone;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlterarUsuarioUseCase extends UseCase<Input, Output> {

    private final UsuarioPort usuarioPort;
    private final UsuarioMapper mapper;

    public Output execute(Input input) {

        var confirmarEmail = false;
        var confirmarTelefone = false;

        var usuarioAtualizar = with(
                input.id,
                input.nome,
                input.email,
                input.telefone
        );

        var usuarioFounded = usuarioPort.buscarPorId(UUID.fromString(input.id))
                .orElseThrow(() -> new BusinessException(ERRO_ID_INVALIDO, input.id));

        var usuario = Usuario.canonicalOf(
                usuarioFounded.getId(),
                usuarioFounded.getNome(),
                usuarioFounded.getEmail(),
                usuarioFounded.isEmailConfirmado(),
                usuarioFounded.getSenha(),
                usuarioFounded.getTelefone(),
                usuarioFounded.getRoles(),
                usuarioFounded.getSituacao(),
                usuarioFounded.getAvatarFilename(),
                usuarioFounded.getAvatarUrl(),
                usuarioFounded.getCreatedAt(),
                usuarioFounded.getUpdatedBy(),
                usuarioFounded.getUpdatedAt(),
                null);


        if (!usuario.isEmailConfirmado()) {
            throw new BusinessException(ERRO_EMAIL_NAO_CONFIRMADO, input.id);
        }

        if (!Objects.equals(usuarioAtualizar.getEmail(), usuario.getEmail())) {
            confirmarEmail = true;
            usuario.setEmail(usuarioAtualizar.getEmail());
            usuario.setEmailConfirmado(false);
        }

        if (!Objects.equals(usuarioAtualizar.getTelefone(), usuario.getTelefone())) {
            confirmarTelefone = true;
            usuario.setTelefone(usuarioAtualizar.getTelefone());
        }

        usuario.setNome(usuarioAtualizar.getNome());

        var usuarioAtualizado = usuarioPort.atualizar(usuario);

        if (confirmarEmail) {
            usuarioAtualizado.setCodigoConfirmacao(Usuario.validationCodeGenerate());
            usuarioAtualizado.registerEvent(new UserEmailConfirmation(usuarioAtualizado.getId().toString(),
                    usuarioAtualizado.getEmail().value(),
                    usuarioAtualizado.getNome().value(),
                    usuarioAtualizado.getCodigoConfirmacao()));
            usuarioPort.criarValidacaoEmail(usuarioAtualizado);
        }

        if (confirmarTelefone) {
            //TODO: implementar fluxo de confirmação de telefone
        }

        return mapper.toAlterarUsuarioOutput(usuarioAtualizado);
    }

    public record Input(
            String id,
            String nome,
            String email,
            Telefone telefone
    ) {

    }

    public record Output(
            String id,
            String nome,
            String email,
            boolean emailConfirmado,
            String situacao,
            Telefone telefone,
            String avatarFilename,
            String avatarUrl,
            String createdAt,
            String updatedBy,
            String updatedAt
    ) {

    }
}
