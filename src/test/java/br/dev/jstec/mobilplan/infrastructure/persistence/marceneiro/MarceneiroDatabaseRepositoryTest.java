package br.dev.jstec.mobilplan.infrastructure.persistence.marceneiro;

import static br.dev.jstec.mobilplan.application.domain.marceneiro.MarceneiroFixture.buildComAuditoria;
import static br.dev.jstec.mobilplan.application.util.RandomHelper.gerarEmail;
import static br.dev.jstec.mobilplan.application.util.RandomHelper.gerarStringNumerica;
import static br.dev.jstec.mobilplan.infrastructure.persistence.marceneiro.MarceneiroEntityFixture.buildComMarceneiro;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import br.dev.jstec.mobilplan.application.domain.valueobject.Email;
import br.dev.jstec.mobilplan.infrastructure.jpa.MarceneiroJpaRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class MarceneiroDatabaseRepositoryTest {

    @Mock
    private MarceneiroJpaRepository repository;

    @Spy
    private MarceneiroEntityMapperImpl mapper;

    @InjectMocks
    MarceneiroDatabaseRepository databaseRepository;

    @Test
    @DisplayName("Deve buscar marceneiro por ID")
    void buscarPorId() {

        var id = UUID.randomUUID();
        var marceneiro = buildComAuditoria();
        var entity = buildComMarceneiro(marceneiro);

        doReturn(of(entity)).when(repository).findById(id);

        var result = databaseRepository.buscarPorId(id);

        assertTrue(result.isPresent());
        result.ifPresent(marceneiroEncontrado -> assertEquals(marceneiro, marceneiroEncontrado));

        verify(repository).findById(any());
        verify(mapper).toMarceneiro(entity);
    }

    @Test
    @DisplayName("Deve retornar vazio quando o ID não existir")
    void buscarPorIdNaoExistente() {

        var id = UUID.randomUUID();

        doReturn(empty()).when(repository).findById(id);

        var result = databaseRepository.buscarPorId(id);

        assertTrue(result.isEmpty());
        verify(repository).findById(id);
        verify(mapper, never()).toMarceneiroEntity(any());
    }

    @Test
    @DisplayName("Deve buscar marceneiro por e-mail")
    void buscarPorEmail() {
        var email = new Email(gerarEmail(true));
        var marceneiro = buildComAuditoria();
        var entity = buildComMarceneiro(marceneiro);

        doReturn(of(entity)).when(repository).findByEmail(email.value());

        var result = databaseRepository.buscarPorEmail(email);

        assertTrue(result.isPresent());
        result.ifPresent(marceneiroEncontrado -> assertEquals(marceneiro, marceneiroEncontrado));

        verify(repository).findByEmail(email.value());
        verify(mapper).toMarceneiro(entity);
    }

    @Test
    @DisplayName("Deve retornar vazio quando o e-mail não existir")
    void buscarPorEmailNaoExistente() {

        var email = new Email(gerarEmail(true));

        doReturn(empty()).when(repository).findByEmail(email.value());

        var result = databaseRepository.buscarPorEmail(email);

        assertTrue(result.isEmpty());

        verify(repository).findByEmail(email.value());
        verify(mapper, never()).toMarceneiroEntity(any());
    }

    @Test
    @DisplayName("Deve buscar marceneiro por documento")
    void buscarPorDocumento() {

        var documento = gerarStringNumerica(11);
        var marceneiro = buildComAuditoria();
        var entity = buildComMarceneiro(marceneiro);

        doReturn(of(entity)).when(repository).findByDocumento(documento);

        var result = databaseRepository.buscarPorDocumento(documento);

        assertTrue(result.isPresent());
        result.ifPresent(marceneiroEncontrado -> assertEquals(marceneiro, marceneiroEncontrado));

        verify(repository).findByDocumento(documento);
        verify(mapper).toMarceneiro(entity);
    }

    @Test
    @DisplayName("Deve retornar vazio quando o documento não existir")
    void buscarPorDocumentoNaoExistente() {

        var documento = gerarStringNumerica(11);

        doReturn(empty()).when(repository).findByDocumento(documento);

        var result = databaseRepository.buscarPorDocumento(documento);

        assertTrue(result.isEmpty());

        verify(repository).findByDocumento(documento);
        verify(mapper, never()).toMarceneiroEntity(any());
    }

    @Test
    @DisplayName("Deve salvar o marceneiro")
    void salvar() {

        var marceneiro = buildComAuditoria();
        var entity = buildComMarceneiro(marceneiro);

        doReturn(entity).when(repository).save(entity);

        var result = databaseRepository.salvar(marceneiro);

        assertEquals(marceneiro, result);

        verify(repository).save(entity);
        verify(mapper).toMarceneiroEntity(marceneiro);
        verify(mapper).toMarceneiro(entity);
    }

    @Test
    @DisplayName("Deve buscar todos os marceneiros")
    void buscarTodosTest() {

        var marceneiro = buildComAuditoria();
        var entity = buildComMarceneiro(marceneiro);

        var marceneirosEntity = List.of(entity);

        doReturn(marceneirosEntity).when(repository).findAll();
        doReturn(marceneiro).when(mapper).toMarceneiro(any(MarceneiroEntity.class));

        var resultado = databaseRepository.buscarTodos();

        assertEquals(marceneirosEntity.size(), resultado.size());
        assertTrue(resultado.contains(marceneiro));

        verify(mapper).toMarceneiro(any());
    }
}