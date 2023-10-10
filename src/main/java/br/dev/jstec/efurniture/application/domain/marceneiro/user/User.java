package br.dev.jstec.efurniture.application.domain.marceneiro.user;

import br.dev.jstec.efurniture.application.domain.marceneiro.MarceneiroId;
import br.dev.jstec.efurniture.application.domain.valueobject.Email;
import br.dev.jstec.efurniture.application.domain.valueobject.Nome;
import br.dev.jstec.efurniture.application.domain.valueobject.Telefone;

public class User {

    private UserId id;
    private Nome nome;
    private Email email;
    private Telefone telefone;
    private MarceneiroId marceneiro;
    private boolean ativo;

}
