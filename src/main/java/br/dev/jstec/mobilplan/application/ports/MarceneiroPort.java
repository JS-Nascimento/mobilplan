package br.dev.jstec.mobilplan.application.ports;

import br.dev.jstec.mobilplan.domain.model.marceneiro.Marceneiro;
import br.dev.jstec.mobilplan.domain.valueobject.Email;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface MarceneiroPort {

    Optional<Marceneiro> buscarPorId(UUID anId);

    Optional<Marceneiro> buscarPorEmail(Email email);

    Optional<Marceneiro> buscarPorDocumento(String documento);

    Marceneiro salvar(Marceneiro marceneiro);

    Collection<Marceneiro> buscarTodos();

    String salvarLogomarca(Marceneiro marceneiro, String fileName, String tipoImagem,
        BufferedImage image)
        throws IOException, URISyntaxException;
}
