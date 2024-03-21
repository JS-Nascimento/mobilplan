package br.dev.jstec.mobilplan.application.ports;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

public interface MateriaPrimaPort<T> {

    Optional<T> buscarPorId(Long id);

    T salvar(T model);

    void remover(T model);

    Collection<T> buscar(Object... criterios);

    boolean existe(T model);

    String salvarImagem(T model, String fileName,
                        String tipoImagem, BufferedImage image)
            throws IOException, URISyntaxException;
}
