package com.example.teste1.service;

import com.example.teste1.entity.Locacao;
import com.example.teste1.repository.LocacaoRepository;
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
class LocacaoServiceTest {

    @Mock
    private LocacaoRepository locacaoRepository;

    @InjectMocks
    private LocacaoService locacaoService;

    private Locacao locacao;

    @BeforeEach
    void setup() {
        locacao = new Locacao();
        locacao.setData_inicio("2025-10-01");
        locacao.setData_fim("2025-10-05");
        locacao.setHoras("40");
        locacao.setStatus("Ativa");
        locacao.setValor_total("1500.00");
        locacao.setCliente_id(1);
        locacao.setTotem_id(2);
    }

    @Test
    @DisplayName("Deve salvar locação com sucesso")
    void testSave() {
        when(locacaoRepository.save(locacao)).thenReturn(locacao);
        String mensagem = locacaoService.save(locacao);
        assertThat(mensagem).isEqualTo("Locação salva com sucesso!");
        verify(locacaoRepository).save(locacao);
    }

    @Test
    @DisplayName("Deve encontrar locação por id com sucesso")
    void testFindByIdSuccess() {
        when(locacaoRepository.findById(1)).thenReturn(Optional.of(locacao));
        Locacao encontrada = locacaoService.findById(1);
        assertThat(encontrada).isNotNull();
        assertThat(encontrada.getStatus()).isEqualTo("Ativa");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar locação por id inexistente")
    void testFindByIdNotFound() {
        when(locacaoRepository.findById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> locacaoService.findById(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Locação não encontrada com o ID: 1");
    }

    @Test
    @DisplayName("Deve listar todas as locações")
    void testFindAll() {
        when(locacaoRepository.findAll()).thenReturn(List.of(locacao));
        List<Locacao> lista = locacaoService.findAll();
        assertThat(lista).isNotEmpty();
        verify(locacaoRepository).findAll();
    }

    @Test
    @DisplayName("Deve atualizar locação com sucesso")
    void testUpdateSuccess() {
        Locacao atualizada = new Locacao();
        atualizada.setData_inicio("2025-11-01");
        atualizada.setData_fim("2025-11-10");
        atualizada.setHoras("45");
        atualizada.setStatus("Concluida");
        atualizada.setValor_total("2000.00");
        atualizada.setCliente_id(3);
        atualizada.setTotem_id(4);

        when(locacaoRepository.findById(1)).thenReturn(Optional.of(locacao));
        when(locacaoRepository.save(any(Locacao.class))).thenReturn(atualizada);

        String mensagem = locacaoService.update(1, atualizada);
        assertThat(mensagem).isEqualTo("Locação atualizada com sucesso!");

        ArgumentCaptor<Locacao> captor = ArgumentCaptor.forClass(Locacao.class);
        verify(locacaoRepository).save(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo("Concluida");
        assertThat(captor.getValue().getValor_total()).isEqualTo("2000.00");
        assertThat(captor.getValue().getCliente_id()).isEqualTo(3);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar locação inexistente")
    void testUpdateNotFound() {
        when(locacaoRepository.findById(1)).thenReturn(Optional.empty());
        Locacao nova = new Locacao();
        assertThatThrownBy(() -> locacaoService.update(1, nova))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Locação não encontrada para atualização");
    }

    @Test
    @DisplayName("Deve deletar locação com sucesso")
    void testDeleteSuccess() {
        when(locacaoRepository.existsById(1)).thenReturn(true);
        doNothing().when(locacaoRepository).deleteById(1);
        String mensagem = locacaoService.delete(1);
        assertThat(mensagem).isEqualTo("Locação deletada com sucesso!");
        verify(locacaoRepository).deleteById(1);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar locação inexistente")
    void testDeleteNotFound() {
        when(locacaoRepository.existsById(1)).thenReturn(false);
        assertThatThrownBy(() -> locacaoService.delete(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Locação não encontrada para exclusão");
    }
}
