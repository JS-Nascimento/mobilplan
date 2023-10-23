package br.dev.jstec.efurniture.application.domain.usuario;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
@Setter
@Value
public class Role {

    Long id;
    String papel;
}
