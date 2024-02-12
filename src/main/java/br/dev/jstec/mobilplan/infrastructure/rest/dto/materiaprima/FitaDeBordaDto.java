package br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
@JsonInclude(NON_NULL)
public class FitaDeBordaDto {
        Long id;
        String descricao;
        String cor;
        String largura;
        double preco;
        String unidade;
        String tipoAcabamento;
        String precificacao;
        @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime criadoEm;
        @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime atualizadoEm;
        UUID tenantId;
}
