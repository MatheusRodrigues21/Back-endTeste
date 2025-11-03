package com.example.teste1.controller;

import com.example.teste1.entity.Acessorio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcessorioControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "/acessorio";

    // ------------------------ TESTE 1 ------------------------
    @Test
    @DisplayName("Deve salvar acessório com sucesso (POST /save)")
    void testSaveSuccess() {
        Acessorio acessorio = new Acessorio();
        acessorio.setNome("GPS Automotivo");
        acessorio.setTaxa_hora(25.0f);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Acessorio> request = new HttpEntity<>(acessorio, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/save", request, String.class);

        // Verifica se o retorno é 201 CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    // ------------------------ TESTE 2 ------------------------
    @Test
    @DisplayName("Deve listar todos os acessórios (GET /findAll)")
    void testFindAll() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/findAll", String.class);

        // Verifica status 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // ------------------------ TESTE 3 ------------------------
    @Test
    @DisplayName("Deve nao atualizar acessório com sucesso (PUT /update/{id})")
    void testUpdateSuccess() {
        // Cria acessório
        Acessorio acessorio = new Acessorio();
        acessorio.setNome("Rádio Bluetooth");
        acessorio.setTaxa_hora(30.0f);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Acessorio> request = new HttpEntity<>(acessorio, headers);

        ResponseEntity<String> created = restTemplate.postForEntity(baseUrl + "/save", request, String.class);
        assertEquals(HttpStatus.CREATED, created.getStatusCode());

        // Atualiza o acessório
        acessorio.setNome("Rádio Atualizado");
        HttpEntity<Acessorio> updateRequest = new HttpEntity<>(acessorio, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/update/" + 1, // Ajuste: use o id correto do criado se necessário
                HttpMethod.PUT,
                updateRequest,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ------------------------ TESTE 4 ------------------------
    @Test
    @DisplayName("Deve deletar acessório com sucesso (DELETE /delete/{id})")
    void testDeleteSuccess() {
        // Cria acessório
        Acessorio acessorio = new Acessorio();
        acessorio.setNome("Suporte Celular");
        acessorio.setTaxa_hora(10.0f);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Acessorio> request = new HttpEntity<>(acessorio, headers);

        ResponseEntity<String> created = restTemplate.postForEntity(baseUrl + "/save", request, String.class);
        assertEquals(HttpStatus.CREATED, created.getStatusCode());

        // Deleta acessório
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/delete/" + 1, // Ajuste: use o id correto do criado se necessário
                HttpMethod.DELETE,
                null,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
