package com.example.teste1.service;

import com.example.teste1.entity.Acessorio;
import com.example.teste1.repository.AcessorioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AcessorioServiceTest {

    @Mock
    private AcessorioRepository acessorioRepository;

    @InjectMocks
    private AcessorioService acessorioService;

    private Acessorio acessorio;

    @BeforeEach
    void setup() {
        acessorio = new Acessorio();
        acessorio.setNome("Acessório Teste");
        acessorio.setTaxa_hora(50.0f);
    }

    @Test
    @DisplayName("Deve salvar acessorio com sucesso")
    void testSave() {
        when(acessorioRepository.save(acessorio)).thenReturn(acessorio);
        String resultado = acessorioService.save(acessorio);
        assertThat(resultado).isEqualTo("Acessório salvo com sucesso!");
        verify(acessorioRepository).save(acessorio);
    }

    @Test
    @DisplayName("Deve encontrar acessorio por id com sucesso")
    void testFindByIdSuccess() {
        when(acessorioRepository.findById(1)).thenReturn(Optional.of(acessorio));
        Acessorio encontrado = acessorioService.findById(1);
        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getNome()).isEqualTo("Acessório Teste");
        assertThat(encontrado.getTaxa_hora()).isEqualTo(50.0f);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar acessorio por id inexistente")
    void testFindByIdNotFound() {
        when(acessorioRepository.findById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> acessorioService.findById(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Acessório não encontrado com o ID: 1");
    }

    @Test
    @DisplayName("Deve listar todos os acessorios")
    void testFindAll() {
        when(acessorioRepository.findAll()).thenReturn(List.of(acessorio));
        List<Acessorio> lista = acessorioService.findAll();
        assertThat(lista).isNotEmpty();
        verify(acessorioRepository).findAll();
    }

    @Test
    @DisplayName("Deve atualizar acessorio com sucesso")
    void testUpdateSuccess() {
        Acessorio atualizado = new Acessorio();
        atualizado.setNome("Novo Nome");
        atualizado.setTaxa_hora(60.0f);

        when(acessorioRepository.findById(1)).thenReturn(Optional.of(acessorio));
        when(acessorioRepository.save(any(Acessorio.class))).thenReturn(atualizado);

        String mensagem = acessorioService.update(1, atualizado);
        assertThat(mensagem).isEqualTo("Acessório atualizado com sucesso!");

        ArgumentCaptor<Acessorio> captor = ArgumentCaptor.forClass(Acessorio.class);
        verify(acessorioRepository).save(captor.capture());
        assertThat(captor.getValue().getNome()).isEqualTo("Novo Nome");
        assertThat(captor.getValue().getTaxa_hora()).isEqualTo(60.0f);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar acessorio inexistente")
    void testUpdateNotFound() {
        when(acessorioRepository.findById(1)).thenReturn(Optional.empty());
        Acessorio novo = new Acessorio();
        assertThatThrownBy(() -> acessorioService.update(1, novo))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Acessório não encontrado para atualização");
    }

    @Test
    @DisplayName("Deve deletar acessorio com sucesso")
    void testDeleteSuccess() {
        when(acessorioRepository.existsById(1)).thenReturn(true);
        doNothing().when(acessorioRepository).deleteById(1);
        String mensagem = acessorioService.delete(1);
        assertThat(mensagem).isEqualTo("Acessório deletado com sucesso!");
        verify(acessorioRepository).deleteById(1);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar acessorio inexistente")
    void testDeleteNotFound() {
        when(acessorioRepository.existsById(1)).thenReturn(false);
        assertThatThrownBy(() -> acessorioService.delete(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Acessório não encontrado para exclusão");
    }
}
