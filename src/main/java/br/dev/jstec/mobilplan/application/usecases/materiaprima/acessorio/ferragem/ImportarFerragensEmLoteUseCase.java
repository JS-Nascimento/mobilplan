package br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem;

import static br.dev.jstec.mobilplan.application.helpers.CsvToEntityConverter.convert;
import static br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.ImportarFerragensEmLoteUseCase.Input;
import static br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.ImportarFerragensEmLoteUseCase.Output;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.Ferragem;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class ImportarFerragensEmLoteUseCase extends UseCase<Input, Output> {

    private final MateriaPrimaPort<Ferragem> materiaPrima;

    @Override
    public Output execute(Input input) {
        List<String> ferragensNaoImportadas = new ArrayList<>();
        List<String> ferragensExistentes = new ArrayList<>();
        List<Ferragem> ferragensParaSalvar = new ArrayList<>();
        List<Ferragem> ferragensImportadas = new ArrayList<>();

        var listaByCsv = convert(input.file, FerragemCsv.class);

        listaByCsv.forEach(ferragemCsv -> {
            if (Ferragem.validateCsvImport(
                    ferragemCsv.descricao,
                    ferragemCsv.cor,
                    ferragemCsv.unidade,
                    ferragemCsv.preco,
                    ferragemCsv.precificacao)) {
                ferragensParaSalvar.add(Ferragem.of(
                        ferragemCsv.descricao,
                        ferragemCsv.cor,
                        ferragemCsv.unidade,
                        ferragemCsv.preco,
                        ferragemCsv.precificacao,
                        null,
                        input.tenantId));
            } else {
                ferragensNaoImportadas.add(ferragemCsv.descricao + " - " + ferragemCsv.cor);
            }
        });

        ferragensParaSalvar.forEach(ferragem -> {
            if (materiaPrima.existe(ferragem)) {
                ferragensExistentes.add(ferragem.getDescricao() + " - " + ferragem.getCor());
            } else {
                ferragensImportadas.add(materiaPrima.salvar(ferragem));
            }
        });

        return new Output(
                ferragensImportadas.stream().map(ferragem -> ferragem.getDescricao() + " - " + ferragem.getCor()
                ).toList(),
                ferragensExistentes,
                ferragensNaoImportadas
        );
    }

    public record Input(MultipartFile file, UUID tenantId) {

    }

    public record Output(
            List<String> ferragensImportadas,
            List<String> ferragensExistentes,
            List<String> ferragensNaoImportadas
    ) {
    }

    @NoArgsConstructor
    public static class FerragemCsv {
        public String descricao;
        public String cor;
        public String unidade;
        public double preco;
        public String precificacao;
    }
}
