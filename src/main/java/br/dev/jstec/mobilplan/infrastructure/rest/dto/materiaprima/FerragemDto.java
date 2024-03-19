package br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class FerragemDto {
    Long id;
    String descricao;
    String cor;
    String unidade;
    double preco;
    String precificacao;
    String imagem;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime criadoEm;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime atualizadoEm;
    UUID tenantId;
}
