package com.example.teste1.controller;

import com.example.teste1.entity.Cliente;
import com.example.teste1.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class) // Configura o teste focando no controller ClienteController
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc; // Mock para simular requisições HTTP

    @Autowired
    private ObjectMapper mapper; // Para converter objetos em JSON e vice-versa

    @MockitoBean
    private ClienteService clienteService; // Instância mockada do service para simular dependências

    // Teste para verificar se um cliente é salvo com sucesso via POST
    @Test
    @DisplayName("Deve salvar cliente com sucesso")
    void testSaveSuccess() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("Cliente Teste");

        // Configura mock: quando salvar cliente, retorna mensagem de sucesso
        Mockito.when(clienteService.save(Mockito.any(Cliente.class)))
                .thenReturn("Cliente salvo com sucesso");

        // Converte o cliente para JSON
        String json = mapper.writeValueAsString(cliente);

        // Realiza requisição POST para salvar e espera status 201 e a mensagem correta
        mockMvc.perform(post("/cliente/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string("Cliente salvo com sucesso"));
    }

    // Teste para verificar tratamento de exceção ao salvar cliente
    @Test
    @DisplayName("Deve retornar erro ao salvar cliente com exceção")
    void testSaveError() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Erro");

        // Configura mock para lançar exceção ao salvar cliente
        Mockito.when(clienteService.save(Mockito.any(Cliente.class)))
                .thenThrow(new RuntimeException("Erro no serviço"));

        String json = mapper.writeValueAsString(cliente);

        // Espera status 400 e mensagem de erro apropriada na resposta
        mockMvc.perform(post("/cliente/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Erro ao salvar cliente: Erro no serviço")));
    }

    // Teste para buscar cliente pelo id com sucesso
    @Test
    @DisplayName("Deve buscar cliente por id com sucesso")
    void testFindByIdSuccess() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("Cliente Encontrado");

        // Mock para o serviço retornar o cliente esperado
        Mockito.when(clienteService.findById(1)).thenReturn(cliente);

        // Requisição GET e validação do JSON retornado
        mockMvc.perform(get("/cliente/findById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Cliente Encontrado"));
    }

    // Teste para buscar cliente e retornar erro caso não encontrado
    @Test
    @DisplayName("Deve retornar erro ao buscar cliente por id não encontrado")
    void testFindByIdNotFound() throws Exception {
        // Mock lança exceção simulando cliente não encontrado
        Mockito.when(clienteService.findById(1)).thenThrow(new RuntimeException("Cliente não encontrado"));

        // Espera status 404 e mensagem de erro na resposta
        mockMvc.perform(get("/cliente/findById/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Erro ao buscar cliente: Cliente não encontrado")));
    }

    // Teste para listar todos os clientes com sucesso
    @Test
    @DisplayName("Deve listar todos os clientes")
    void testFindAll() throws Exception {
        Cliente c1 = new Cliente();
        c1.setId(1);
        c1.setNome("C1");
        Cliente c2 = new Cliente();
        c2.setId(2);
        c2.setNome("C2");

        // Mock retorna uma lista com dois clientes
        Mockito.when(clienteService.findAll()).thenReturn(Arrays.asList(c1, c2));

        // Requisição GET e validação dos clientes retornados no JSON
        mockMvc.perform(get("/cliente/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    // Teste para atualizar cliente com sucesso via PUT
    @Test
    @DisplayName("Deve atualizar cliente com sucesso")
    void testUpdateSuccess() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("Atualizado");

        // Mock para método update retornar mensagem de sucesso
        Mockito.when(clienteService.update(Mockito.eq(1), Mockito.any(Cliente.class)))
                .thenReturn("Cliente atualizado com sucesso");

        String json = mapper.writeValueAsString(cliente);

        // Requisição PUT esperando status 200 e mensagem de sucesso
        mockMvc.perform(put("/cliente/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Cliente atualizado com sucesso"));
    }

    // Teste para atualizar cliente e simular exceção no serviço
    @Test
    @DisplayName("Deve retornar erro ao atualizar cliente com exceção")
    void testUpdateError() throws Exception {
        Cliente cliente = new Cliente();

        // Mock lançando exceção ao tentar atualizar
        Mockito.when(clienteService.update(Mockito.eq(1), Mockito.any(Cliente.class)))
                .thenThrow(new RuntimeException("Erro na atualização"));

        String json = mapper.writeValueAsString(cliente);

        // Espera status 400 e mensagem de erro na resposta
        mockMvc.perform(put("/cliente/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Erro ao atualizar cliente: Erro na atualização")));
    }

    // Teste para deletar cliente com sucesso via DELETE
    @Test
    @DisplayName("Deve deletar cliente com sucesso")
    void testDeleteSuccess() throws Exception {
        // Mock para método delete retornar mensagem de sucesso
        Mockito.when(clienteService.delete(1))
                .thenReturn("Cliente deletado com sucesso");

        // Requisição DELETE esperando status 200 e mensagem de sucesso
        mockMvc.perform(delete("/cliente/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cliente deletado com sucesso"));
    }

    // Teste para deletar cliente e simular exceção no serviço
    @Test
    @DisplayName("Deve retornar erro ao deletar cliente com exceção")
    void testDeleteError() throws Exception {
        // Mock lançando exceção ao tentar deletar
        Mockito.when(clienteService.delete(1))
                .thenThrow(new RuntimeException("Erro na deleção"));

        // Espera status 400 e mensagem de erro na resposta
        mockMvc.perform(delete("/cliente/delete/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Erro ao deletar cliente: Erro na deleção")));
    }
}
