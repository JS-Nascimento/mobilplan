package br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_CONVERTER_IMAGEM;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_SALVAR_IMAGEM;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.Mdf;
import br.dev.jstec.mobilplan.domain.valueobject.Imagem;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

@RequiredArgsConstructor
public class SalvarImagemMdfUseCase extends UseCase<SalvarImagemMdfUseCase.Input, SalvarImagemMdfUseCase.Output> {

    private static final Set<String> SUPPORTED_CONTENT_TYPES = Set.of(
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_JPEG_VALUE
    );
    private final MateriaPrimaPort<Mdf> materiaPrima;

    @Override
    public Output execute(Input input) {

        if (!SUPPORTED_CONTENT_TYPES.contains(input.tipoImagem())) {
            throw new BusinessException(ERRO_CONVERTER_IMAGEM);
        }

        var imagem = Imagem.of(Long.toString(input.id()), input.inputStream());

        return materiaPrima.buscarPorId(input.id())
                .map(ferragem -> {
                    try {
                        return new Output(
                                materiaPrima.salvarImagem(
                                        ferragem,
                                        imagem.fileName(),
                                        input.tipoImagem(),
                                        imagem.image()
                                )
                        );
                    } catch (IOException e) {

                        throw new BusinessException(ERRO_CONVERTER_IMAGEM);

                    } catch (URISyntaxException e) {

                        throw new BusinessException(ERRO_SALVAR_IMAGEM);
                    }
                })
                .orElseThrow(() -> new BusinessException(ERRO_ID_INVALIDO, Long.toString(input.id())));
    }

    public record Input(
            long id,
            String tipoImagem,
            InputStream inputStream) {

    }

    public record Output(String imagem) {
    }
}
