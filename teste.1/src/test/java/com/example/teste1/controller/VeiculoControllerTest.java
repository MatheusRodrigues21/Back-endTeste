package com.example.teste1.controller;

import com.example.teste1.entity.Veiculo;
import com.example.teste1.service.VeiculoService;
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

@WebMvcTest(VeiculoController.class)
class VeiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private VeiculoService veiculoService;

    @Test
    @DisplayName("Deve salvar veiculo com sucesso")
    void testSaveSuccess() throws Exception {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        // Inicialize outros campos conforme sua entidade Veiculo, ex:
        // veiculo.setModelo("ModeloX");
        // veiculo.setPlaca("ABC-1234");

        Mockito.when(veiculoService.save(Mockito.any(Veiculo.class)))
                .thenReturn("Veículo salvo com sucesso");

        String json = mapper.writeValueAsString(veiculo);

        mockMvc.perform(post("/api/veiculo/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string("Veículo salvo com sucesso"));
    }

    @Test
    @DisplayName("Deve retornar erro ao salvar veiculo com exceção")
    void testSaveError() throws Exception {
        Veiculo veiculo = new Veiculo();

        Mockito.when(veiculoService.save(Mockito.any(Veiculo.class)))
                .thenThrow(new RuntimeException("Erro no serviço"));

        String json = mapper.writeValueAsString(veiculo);

        mockMvc.perform(post("/api/veiculo/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Erro ao salvar veículo: Erro no serviço")));
    }

    @Test
    @DisplayName("Deve buscar veiculo por id com sucesso")
    void testFindByIdSuccess() throws Exception {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        // Inicialize outros campos conforme sua entidade Veiculo

        Mockito.when(veiculoService.findById(1L)).thenReturn(veiculo);

        mockMvc.perform(get("/api/veiculo/findById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        // Verifique campos adicionais conforme sua entidade
    }

    @Test
    @DisplayName("Deve retornar erro ao buscar veiculo por id não encontrado")
    void testFindByIdNotFound() throws Exception {
        Mockito.when(veiculoService.findById(1L)).thenThrow(new RuntimeException("Veículo não encontrado"));

        mockMvc.perform(get("/api/veiculo/findById/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Erro ao buscar veículo: Veículo não encontrado")));
    }

    @Test
    @DisplayName("Deve listar todos os veiculos")
    void testFindAll() throws Exception {
        Veiculo v1 = new Veiculo();
        v1.setId(1L);
        Veiculo v2 = new Veiculo();
        v2.setId(2L);

        Mockito.when(veiculoService.findAll()).thenReturn(Arrays.asList(v1, v2));

        mockMvc.perform(get("/api/veiculo/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    @DisplayName("Deve atualizar veiculo com sucesso")
    void testUpdateSuccess() throws Exception {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        // Defina outros campos se houver

        Mockito.when(veiculoService.update(Mockito.eq(1L), Mockito.any(Veiculo.class)))
                .thenReturn("Veículo atualizado com sucesso");

        String json = mapper.writeValueAsString(veiculo);

        mockMvc.perform(put("/api/veiculo/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Veículo atualizado com sucesso"));
    }

    @Test
    @DisplayName("Deve retornar erro ao atualizar veiculo com exceção")
    void testUpdateError() throws Exception {
        Veiculo veiculo = new Veiculo();

        Mockito.when(veiculoService.update(Mockito.eq(1L), Mockito.any(Veiculo.class)))
                .thenThrow(new RuntimeException("Erro na atualização"));

        String json = mapper.writeValueAsString(veiculo);

        mockMvc.perform(put("/api/veiculo/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Erro ao atualizar veículo: Erro na atualização")));
    }

    @Test
    @DisplayName("Deve deletar veiculo com sucesso")
    void testDeleteSuccess() throws Exception {
        Mockito.when(veiculoService.delete(1L))
                .thenReturn("Veículo deletado com sucesso");

        mockMvc.perform(delete("/api/veiculo/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Veículo deletado com sucesso"));
    }

    @Test
    @DisplayName("Deve retornar erro ao deletar veiculo com exceção")
    void testDeleteError() throws Exception {
        Mockito.when(veiculoService.delete(1L))
                .thenThrow(new RuntimeException("Erro na deleção"));

        mockMvc.perform(delete("/api/veiculo/delete/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Erro ao deletar veículo: Erro na deleção")));
    }
}
