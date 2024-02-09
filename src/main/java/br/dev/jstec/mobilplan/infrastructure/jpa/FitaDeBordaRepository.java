package br.dev.jstec.mobilplan.infrastructure.jpa;

import br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima.FitaDeBordaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FitaDeBordaRepository extends JpaRepository<FitaDeBordaEntity, Long> {
}
