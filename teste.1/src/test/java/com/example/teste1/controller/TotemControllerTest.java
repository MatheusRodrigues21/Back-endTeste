package com.example.teste1.controller;

import com.example.teste1.entity.Totem;
import com.example.teste1.service.TotemService;
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

@WebMvcTest(TotemController.class)
class TotemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private TotemService totemService;

    @Test
    @DisplayName("Deve salvar totem com sucesso")
    void testSaveSuccess() throws Exception {
        Totem totem = new Totem();
        totem.setId(1);
        totem.setCodigo(123);
        totem.setLocal("Entrada Principal");

        Mockito.when(totemService.save(Mockito.any(Totem.class)))
                .thenReturn("Totem salvo com sucesso");

        String json = mapper.writeValueAsString(totem);

        mockMvc.perform(post("/totem/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string("Totem salvo com sucesso"));
    }

    @Test
    @DisplayName("Deve retornar erro ao salvar totem com exceção")
    void testSaveError() throws Exception {
        Totem totem = new Totem();

        Mockito.when(totemService.save(Mockito.any(Totem.class)))
                .thenThrow(new RuntimeException("Erro no serviço"));

        String json = mapper.writeValueAsString(totem);

        mockMvc.perform(post("/totem/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Erro ao salvar totem: Erro no serviço")));
    }

    @Test
    @DisplayName("Deve buscar totem por id com sucesso")
    void testFindByIdSuccess() throws Exception {
        Totem totem = new Totem();
        totem.setId(1);
        totem.setCodigo(123);
        totem.setLocal("Entrada Principal");

        Mockito.when(totemService.findById(1)).thenReturn(totem);

        mockMvc.perform(get("/totem/findById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.codigo").value(123))
                .andExpect(jsonPath("$.local").value("Entrada Principal"));
    }

    @Test
    @DisplayName("Deve retornar erro ao buscar totem por id não encontrado")
    void testFindByIdNotFound() throws Exception {
        Mockito.when(totemService.findById(1)).thenThrow(new RuntimeException("Totem não encontrado"));

        mockMvc.perform(get("/totem/findById/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Erro ao buscar totem: Totem não encontrado")));
    }

    @Test
    @DisplayName("Deve listar todos os totems")
    void testFindAll() throws Exception {
        Totem t1 = new Totem();
        t1.setId(1);
        t1.setCodigo(111);
        t1.setLocal("Portão 1");
        Totem t2 = new Totem();
        t2.setId(2);
        t2.setCodigo(222);
        t2.setLocal("Portão 2");

        Mockito.when(totemService.findAll()).thenReturn(Arrays.asList(t1, t2));

        mockMvc.perform(get("/totem/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].codigo").value(111))
                .andExpect(jsonPath("$[0].local").value("Portão 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].codigo").value(222))
                .andExpect(jsonPath("$[1].local").value("Portão 2"));
    }

    @Test
    @DisplayName("Deve atualizar totem com sucesso")
    void testUpdateSuccess() throws Exception {
        Totem totem = new Totem();
        totem.setId(1);
        totem.setCodigo(333);
        totem.setLocal("Entrada Atualizada");

        Mockito.when(totemService.update(Mockito.eq(1), Mockito.any(Totem.class)))
                .thenReturn("Totem atualizado com sucesso");

        String json = mapper.writeValueAsString(totem);

        mockMvc.perform(put("/totem/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Totem atualizado com sucesso"));
    }

    @Test
    @DisplayName("Deve retornar erro ao atualizar totem com exceção")
    void testUpdateError() throws Exception {
        Totem totem = new Totem();

        Mockito.when(totemService.update(Mockito.eq(1), Mockito.any(Totem.class)))
                .thenThrow(new RuntimeException("Erro na atualização"));

        String json = mapper.writeValueAsString(totem);

        mockMvc.perform(put("/totem/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Erro ao atualizar totem: Erro na atualização")));
    }

    @Test
    @DisplayName("Deve deletar totem com sucesso")
    void testDeleteSuccess() throws Exception {
        Mockito.when(totemService.delete(1))
                .thenReturn("Totem deletado com sucesso");

        mockMvc.perform(delete("/totem/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Totem deletado com sucesso"));
    }

    @Test
    @DisplayName("Deve retornar erro ao deletar totem com exceção")
    void testDeleteError() throws Exception {
        Mockito.when(totemService.delete(1))
                .thenThrow(new RuntimeException("Erro na deleção"));

        mockMvc.perform(delete("/totem/delete/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Erro ao deletar totem: Erro na deleção")));
    }
}
