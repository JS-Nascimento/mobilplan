package br.dev.jstec.mobilplan.infrastructure.rest.dto.auth;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginDto {

    private String username;
    private String password;
}
