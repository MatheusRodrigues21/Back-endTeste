package com.example.teste1.service;

import com.example.teste1.entity.Totem;
import com.example.teste1.repository.TotemRepository;
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
class TotemServiceTest {

    @Mock
    private TotemRepository totemRepository;

    @InjectMocks
    private TotemService totemService;

    private Totem totem;

    @BeforeEach
    void setup() {
        totem = new Totem();
        totem.setCodigo(100);
        totem.setLocal("Entrada Principal");
    }

    @Test
    @DisplayName("Deve salvar totem com sucesso")
    void testSave() {
        when(totemRepository.save(totem)).thenReturn(totem);
        String mensagem = totemService.save(totem);
        assertThat(mensagem).isEqualTo("Totem salvo com sucesso!");
        verify(totemRepository).save(totem);
    }

    @Test
    @DisplayName("Deve encontrar totem por id com sucesso")
    void testFindByIdSuccess() {
        when(totemRepository.findById(1)).thenReturn(Optional.of(totem));
        Totem encontrado = totemService.findById(1);
        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getCodigo()).isEqualTo(100);
        assertThat(encontrado.getLocal()).isEqualTo("Entrada Principal");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar totem por id inexistente")
    void testFindByIdNotFound() {
        when(totemRepository.findById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> totemService.findById(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Totem não encontrado com o ID: 1");
    }

    @Test
    @DisplayName("Deve listar todos os totems")
    void testFindAll() {
        when(totemRepository.findAll()).thenReturn(List.of(totem));
        List<Totem> lista = totemService.findAll();
        assertThat(lista).isNotEmpty();
        verify(totemRepository).findAll();
    }

    @Test
    @DisplayName("Deve atualizar totem com sucesso")
    void testUpdateSuccess() {
        Totem atualizado = new Totem();
        atualizado.setCodigo(200);
        atualizado.setLocal("Portão 2");

        when(totemRepository.findById(1)).thenReturn(Optional.of(totem));
        when(totemRepository.save(any(Totem.class))).thenReturn(atualizado);

        String mensagem = totemService.update(1, atualizado);
        assertThat(mensagem).isEqualTo("Totem atualizado com sucesso!");

        ArgumentCaptor<Totem> captor = ArgumentCaptor.forClass(Totem.class);
        verify(totemRepository).save(captor.capture());
        assertThat(captor.getValue().getCodigo()).isEqualTo(200);
        assertThat(captor.getValue().getLocal()).isEqualTo("Portão 2");
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar totem inexistente")
    void testUpdateNotFound() {
        when(totemRepository.findById(1)).thenReturn(Optional.empty());
        Totem novo = new Totem();
        assertThatThrownBy(() -> totemService.update(1, novo))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Totem não encontrado para atualização");
    }

    @Test
    @DisplayName("Deve deletar totem com sucesso")
    void testDeleteSuccess() {
        when(totemRepository.existsById(1)).thenReturn(true);
        doNothing().when(totemRepository).deleteById(1);
        String mensagem = totemService.delete(1);
        assertThat(mensagem).isEqualTo("Totem deletado com sucesso!");
        verify(totemRepository).deleteById(1);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar totem inexistente")
    void testDeleteNotFound() {
        when(totemRepository.existsById(1)).thenReturn(false);
        assertThatThrownBy(() -> totemService.delete(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Totem não encontrado para exclusão");
    }
}
