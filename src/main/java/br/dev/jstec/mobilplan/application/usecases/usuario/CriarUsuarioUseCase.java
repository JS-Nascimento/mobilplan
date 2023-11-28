package br.dev.jstec.mobilplan.application.usecases.usuario;

import static br.dev.jstec.mobilplan.application.domain.usuario.Usuario.validationCodeGenerate;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_EMAIL_CADASTRADO;

import br.dev.jstec.mobilplan.application.domain.usuario.UserEmailConfirmation;
import br.dev.jstec.mobilplan.application.domain.usuario.Usuario;
import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.repository.UsuarioRepository;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.application.usecases.usuario.CriarUsuarioUseCase.Input;
import br.dev.jstec.mobilplan.application.usecases.usuario.CriarUsuarioUseCase.Output;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriarUsuarioUseCase extends UseCase<Input, Output> {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public Output execute(Input input) {

        var novoUsuario = Usuario.createPrincipalOf(input.nome, input.email, input.senha);

        usuarioRepository.buscarPorEmail(novoUsuario.getEmail().value())
            .ifPresent(usuario -> {
                throw new BusinessException(ERRO_EMAIL_CADASTRADO, input.email);
            });

        var usuarioSalvo = usuarioRepository.criar(novoUsuario);

        usuarioSalvo.setCodigoConfirmacao(validationCodeGenerate());

        usuarioSalvo.registerEvent(new UserEmailConfirmation(
            usuarioSalvo.getId().toString(),
            usuarioSalvo.getEmail().value(),
            usuarioSalvo.getNome().value(),
            usuarioSalvo.getCodigoConfirmacao()));

        usuarioRepository.criarValidacaoEmail(usuarioSalvo);

        return usuarioMapper.toCriarUsuarioOutput(usuarioSalvo);
    }

    public record Input(String nome, String email, String senha) {

    }

    public record Output(String id, String nome, String email, String situacao) {

    }
}
