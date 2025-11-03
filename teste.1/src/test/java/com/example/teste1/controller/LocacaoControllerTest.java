package com.example.teste1.controller;

import com.example.teste1.entity.Locacao;
import com.example.teste1.service.LocacaoService;
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

@WebMvcTest(LocacaoController.class)
class LocacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private LocacaoService locacaoService;

    @Test
    @DisplayName("Deve salvar locacao com sucesso")
    void testSaveSuccess() throws Exception {
        Locacao locacao = new Locacao();
        locacao.setId(1);
        locacao.setData_inicio("2025-10-01");
        locacao.setData_fim("2025-10-05");
        locacao.setHoras("40");
        locacao.setStatus("Ativa");
        locacao.setValor_total("1500.00");
        locacao.setCliente_id(10);
        locacao.setTotem_id(5);

        Mockito.when(locacaoService.save(Mockito.any(Locacao.class)))
                .thenReturn("Locação salva com sucesso");

        String json = mapper.writeValueAsString(locacao);

        mockMvc.perform(post("/locacao/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string("Locação salva com sucesso"));
    }

    @Test
    @DisplayName("Deve retornar erro ao salvar locacao com exceção")
    void testSaveError() throws Exception {
        Locacao locacao = new Locacao();
        locacao.setData_inicio("2025-10-01");

        Mockito.when(locacaoService.save(Mockito.any(Locacao.class)))
                .thenThrow(new RuntimeException("Erro no serviço"));

        String json = mapper.writeValueAsString(locacao);

        mockMvc.perform(post("/locacao/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Erro ao salvar locação: Erro no serviço")));
    }

    @Test
    @DisplayName("Deve buscar locacao por id com sucesso")
    void testFindByIdSuccess() throws Exception {
        Locacao locacao = new Locacao();
        locacao.setId(1);
        locacao.setData_inicio("2025-10-01");
        locacao.setStatus("Ativa");

        Mockito.when(locacaoService.findById(1)).thenReturn(locacao);

        mockMvc.perform(get("/locacao/findById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.data_inicio").value("2025-10-01"))
                .andExpect(jsonPath("$.status").value("Ativa"));
    }

    @Test
    @DisplayName("Deve retornar erro ao buscar locacao por id não encontrado")
    void testFindByIdNotFound() throws Exception {
        Mockito.when(locacaoService.findById(1)).thenThrow(new RuntimeException("Locação não encontrada"));

        mockMvc.perform(get("/locacao/findById/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Erro ao buscar locação: Locação não encontrada")));
    }

    @Test
    @DisplayName("Deve listar todas as locacoes")
    void testFindAll() throws Exception {
        Locacao l1 = new Locacao();
        l1.setId(1);
        l1.setStatus("Ativa");
        Locacao l2 = new Locacao();
        l2.setId(2);
        l2.setStatus("Finalizada");

        Mockito.when(locacaoService.findAll()).thenReturn(Arrays.asList(l1, l2));

        mockMvc.perform(get("/locacao/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].status").value("Ativa"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].status").value("Finalizada"));
    }

    @Test
    @DisplayName("Deve atualizar locacao com sucesso")
    void testUpdateSuccess() throws Exception {
        Locacao locacao = new Locacao();
        locacao.setId(1);
        locacao.setStatus("Atualizada");

        Mockito.when(locacaoService.update(Mockito.eq(1), Mockito.any(Locacao.class)))
                .thenReturn("Locação atualizada com sucesso");

        String json = mapper.writeValueAsString(locacao);

        mockMvc.perform(put("/locacao/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Locação atualizada com sucesso"));
    }

    @Test
    @DisplayName("Deve retornar erro ao atualizar locacao com exceção")
    void testUpdateError() throws Exception {
        Locacao locacao = new Locacao();

        Mockito.when(locacaoService.update(Mockito.eq(1), Mockito.any(Locacao.class)))
                .thenThrow(new RuntimeException("Erro na atualização"));

        String json = mapper.writeValueAsString(locacao);

        mockMvc.perform(put("/locacao/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Erro ao atualizar locação: Erro na atualização")));
    }

    @Test
    @DisplayName("Deve deletar locacao com sucesso")
    void testDeleteSuccess() throws Exception {
        Mockito.when(locacaoService.delete(1))
                .thenReturn("Locação deletada com sucesso");

        mockMvc.perform(delete("/locacao/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Locação deletada com sucesso"));
    }

    @Test
    @DisplayName("Deve retornar erro ao deletar locacao com exceção")
    void testDeleteError() throws Exception {
        Mockito.when(locacaoService.delete(1))
                .thenThrow(new RuntimeException("Erro na deleção"));

        mockMvc.perform(delete("/locacao/delete/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Erro ao deletar locação: Erro na deleção")));
    }
}
