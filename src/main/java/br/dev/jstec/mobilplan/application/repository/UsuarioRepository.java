package br.dev.jstec.mobilplan.application.repository;

import br.dev.jstec.mobilplan.application.domain.usuario.Usuario;
import java.util.Optional;

public interface UsuarioRepository {

    Optional<Usuario> buscarPorEmail(String email);

    Usuario criar(Usuario usuario);

}
