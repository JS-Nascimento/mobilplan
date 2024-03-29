package br.dev.jstec.mobilplan.application.usecases.marceneiro;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_CONVERTER_IMAGEM;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_SALVAR_IMAGEM;
import static br.dev.jstec.mobilplan.domain.valueobject.Imagem.of;
import static java.util.UUID.fromString;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MarceneiroPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.SalvarLogomarcaUseCase.Input;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.SalvarLogomarcaUseCase.Output;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SalvarLogomarcaUseCase extends UseCase<Input, Output> {

    private final MarceneiroPort marceneiroPort;

    @Override
    public Output execute(SalvarLogomarcaUseCase.Input input) {

        var logomarca = of(input.id(), input.inputStream());

        return marceneiroPort.buscarPorId(fromString(input.id()))
                .map(marceneiro -> {
                    try {
                        return new Output(
                                marceneiroPort.salvarLogomarca(
                                        marceneiro,
                                        logomarca.fileName(),
                                        input.tipoImagem(),
                                        logomarca.image()
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
