package br.dev.jstec.mobilplan.application.ports;

import br.dev.jstec.mobilplan.domain.model.usuario.Usuario;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioPort {

    Optional<Usuario> buscarPorEmail(String email);

    Optional<Usuario> buscarPorId(UUID anId);

    Usuario criar(Usuario usuario);

    Usuario atualizar(Usuario usuario);

    void criarValidacaoEmail(Usuario usuario);

    String salvarAvatar(Usuario usuario, String fileName, String tipoImagem,
                        BufferedImage image)
            throws IOException, URISyntaxException;
}
