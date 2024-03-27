package br.dev.jstec.mobilplan.domain.model.componentes;

import br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.Puxador;
import java.util.Optional;

public interface Fechamento extends Componente {

    String getDescricaoCurta();

    Optional<Puxador> getPuxador();
}