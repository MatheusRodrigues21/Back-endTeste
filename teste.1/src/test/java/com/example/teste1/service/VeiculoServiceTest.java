package com.example.teste1.service;

import com.example.teste1.entity.Veiculo;
import com.example.teste1.repository.VeiculoRepository;
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

@ExtendWith(MockitoExtension.class) // Habilita integração com Mockito no JUnit 5
class VeiculoServiceTest {

    @Mock
    private VeiculoRepository veiculoRepository; // Mock do repositório para simular acesso a dados

    @InjectMocks
    private VeiculoService veiculoService; // Classe sob teste com as dependências mockadas

    private Veiculo veiculo; // Objeto Veiculo usado em vários testes

    @BeforeEach
    void setup() {
        // Inicializa um Veículo padrão antes de cada teste
        veiculo = new Veiculo();
        veiculo.setModelo("ModeloX");
        veiculo.setStatus("Ativo");
    }

    // Teste para salvar um veículo com sucesso
    @Test
    @DisplayName("Deve salvar veículo com sucesso")
    void testSave() {
        // Quando salvar o veículo, retorna o próprio veículo
        when(veiculoRepository.save(veiculo)).thenReturn(veiculo);
        // Chama o método save do serviço
        String mensagem = veiculoService.save(veiculo);
        // Verifica a mensagem de sucesso retornad
        assertThat(mensagem).isEqualTo("Veículo salvo com sucesso!");
        // Confirma que o método save do repositório foi chamado uma vez
        verify(veiculoRepository).save(veiculo);
    }

    // Testa buscar veículo por ID com sucesso
    @Test
    @DisplayName("Deve encontrar veículo por id com sucesso")
    void testFindByIdSuccess() {
        // Mock: retorna o veículo encapsulado no Optional
        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));
        // Executa o método findById do serviço
        Veiculo encontrado = veiculoService.findById(1L);
        // Valida que o veículo foi encontrado e campos estão corretos
        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getModelo()).isEqualTo("ModeloX");
        assertThat(encontrado.getStatus()).isEqualTo("Ativo");
    }

    // Testa erro ao buscar veículo por ID inexistente
    @Test
    @DisplayName("Deve lançar exceção ao buscar veículo por id inexistente")
    void testFindByIdNotFound() {
        // Mock retorna Optional vazio simulando veículo não encontrado
        when(veiculoRepository.findById(1L)).thenReturn(Optional.empty());
        // Verifica se a exceção esperada é lançada com a mensagem correta
        assertThatThrownBy(() -> veiculoService.findById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Veículo não encontrado com o ID: 1");
    }

    // Testa listar todos os veículos
    @Test
    @DisplayName("Deve listar todos os veículos")
    void testFindAll() {
        // Mock retorna uma lista com o veículo criado no setup
        when(veiculoRepository.findAll()).thenReturn(List.of(veiculo));
        // Chama método findAll do serviço
        List<Veiculo> lista = veiculoService.findAll();
        // Verifica se a lista não está vazia
        assertThat(lista).isNotEmpty();
        // Verifica chamada do método findAll do repositório
        verify(veiculoRepository).findAll();
    }

    // Testa atualização de veículo com sucesso
    @Test
    @DisplayName("Deve atualizar veículo com sucesso")
    void testUpdateSuccess() {
        Veiculo atualizado = new Veiculo();
        atualizado.setModelo("ModeloY");
        atualizado.setStatus("Inativo");

        // Mock findById para retornar veículo existente (para atualização)
        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));
        // Mock save para retornar veículo atualizado
        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(atualizado);

        // Chama método update do serviço
        String mensagem = veiculoService.update(1L, atualizado);
        // Verifica mensagem de sucesso
        assertThat(mensagem).isEqualTo("Veículo atualizado com sucesso!");

        // Captura o argumento passado para o método save e valida os dados atualizados
        ArgumentCaptor<Veiculo> captor = ArgumentCaptor.forClass(Veiculo.class);
        verify(veiculoRepository).save(captor.capture());
        assertThat(captor.getValue().getModelo()).isEqualTo("ModeloY");
        assertThat(captor.getValue().getStatus()).isEqualTo("Inativo");
    }

    // Testa erro ao tentar atualizar veículo que não existe
    @Test
    @DisplayName("Deve lançar exceção ao atualizar veículo inexistente")
    void testUpdateNotFound() {
        // Mock retorna vazio simulando veículo não encontrado
        when(veiculoRepository.findById(1L)).thenReturn(Optional.empty());
        Veiculo novo = new Veiculo();
        // Verifica se lança RuntimeException com mensagem apropriada
        assertThatThrownBy(() -> veiculoService.update(1L, novo))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Veículo não encontrado para atualização");
    }

    // Testa exclusão de veículo com sucesso
    @Test
    @DisplayName("Deve deletar veículo com sucesso")
    void testDeleteSuccess() {
        // Mock verifica existência do veículo e não faz nada na exclusão
        when(veiculoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(veiculoRepository).deleteById(1L);
        // Chama método delete do serviço
        String mensagem = veiculoService.delete(1L);
        // Verifica mensagem de confirmação
        assertThat(mensagem).isEqualTo("Veículo deletado com sucesso!");
        // Verifica se o método deleteById foi invocado
        verify(veiculoRepository).deleteById(1L);
    }

    // Testa erro ao tentar deletar veículo que não existe
    @Test
    @DisplayName("Deve lançar exceção ao deletar veículo inexistente")
    void testDeleteNotFound() {
        // Mock indica que veículo não existe
        when(veiculoRepository.existsById(1L)).thenReturn(false);
        // Verifica se é lançada exceção com mensagem adequada
        assertThatThrownBy(() -> veiculoService.delete(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Veículo não encontrado para exclusão");
    }
}
