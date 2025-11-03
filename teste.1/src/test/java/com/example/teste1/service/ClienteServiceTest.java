package com.example.teste1.service;

import com.example.teste1.entity.Cliente;
import com.example.teste1.repository.ClienteRepository;
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
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setup() {
        cliente = new Cliente();
        cliente.setCpf("12345678900");
        cliente.setNome("Cliente Teste");
        cliente.setTelefone("999999999");
    }

    @Test
    @DisplayName("Deve salvar cliente com sucesso")
    void testSave() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        String resultado = clienteService.save(cliente);
        assertThat(resultado).isEqualTo("Cliente salvo com sucesso!");
        verify(clienteRepository).save(cliente);
    }

    @Test
    @DisplayName("Deve encontrar cliente por id com sucesso")
    void testFindByIdSuccess() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        Cliente encontrado = clienteService.findById(1);
        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getCpf()).isEqualTo("12345678900");
        assertThat(encontrado.getNome()).isEqualTo("Cliente Teste");
        assertThat(encontrado.getTelefone()).isEqualTo("999999999");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar cliente por id inexistente")
    void testFindByIdNotFound() {
        when(clienteRepository.findById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> clienteService.findById(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Cliente não encontrado com o ID: 1");
    }

    @Test
    @DisplayName("Deve listar todos os clientes")
    void testFindAll() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        List<Cliente> lista = clienteService.findAll();
        assertThat(lista).isNotEmpty();
        verify(clienteRepository).findAll();
    }

    @Test
    @DisplayName("Deve atualizar cliente com sucesso")
    void testUpdateSuccess() {
        Cliente atualizado = new Cliente();
        atualizado.setCpf("09876543210");
        atualizado.setNome("Novo Nome");
        atualizado.setTelefone("888888888");

        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(atualizado);

        String mensagem = clienteService.update(1, atualizado);
        assertThat(mensagem).isEqualTo("Cliente atualizado com sucesso!");

        ArgumentCaptor<Cliente> captor = ArgumentCaptor.forClass(Cliente.class);
        verify(clienteRepository).save(captor.capture());
        assertThat(captor.getValue().getCpf()).isEqualTo("09876543210");
        assertThat(captor.getValue().getNome()).isEqualTo("Novo Nome");
        assertThat(captor.getValue().getTelefone()).isEqualTo("888888888");
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar cliente inexistente")
    void testUpdateNotFound() {
        when(clienteRepository.findById(1)).thenReturn(Optional.empty());
        Cliente novo = new Cliente();
        assertThatThrownBy(() -> clienteService.update(1, novo))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Cliente não encontrado para atualização");
    }

    @Test
    @DisplayName("Deve deletar cliente com sucesso")
    void testDeleteSuccess() {
        when(clienteRepository.existsById(1)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(1);
        String mensagem = clienteService.delete(1);
        assertThat(mensagem).isEqualTo("Cliente deletado com sucesso!");
        verify(clienteRepository).deleteById(1);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar cliente inexistente")
    void testDeleteNotFound() {
        when(clienteRepository.existsById(1)).thenReturn(false);
        assertThatThrownBy(() -> clienteService.delete(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Cliente não encontrado para exclusão");
    }
}
