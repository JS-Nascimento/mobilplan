package br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data

public class PuxadorDto {
    Long id;
    boolean perfil;
    String tipoPuxador;
    String descricao;
    String cor;
    String direcao;
    String unidade;
    double preco;
    String precificacao;
    String imagem;
    double altura;
    double largura;
    double espessura;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime criadoEm;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime atualizadoEm;
    UUID tenantId;
}
