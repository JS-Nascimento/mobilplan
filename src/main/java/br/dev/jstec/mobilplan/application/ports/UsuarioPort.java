package br.dev.jstec.mobilplan.application.ports;

import br.dev.jstec.mobilplan.domain.usuario.Usuario;
import java.util.Optional;

public interface UsuarioPort {

    Optional<Usuario> buscarPorEmail(String email);

    Usuario criar(Usuario usuario);

    void criarValidacaoEmail(Usuario usuario);

}
