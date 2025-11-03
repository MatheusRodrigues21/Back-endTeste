package com.example.teste1.controller;

import com.example.teste1.entity.Acessorio;
import com.example.teste1.service.AcessorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acessorio")
@CrossOrigin(origins = "*")
public class AcessorioController {

    @Autowired
    private AcessorioService acessorioService;

    // CREATE
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Acessorio acessorio) {
        try {
            String mensagem = acessorioService.save(acessorio);
            return new ResponseEntity<>(mensagem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao salvar acessório: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    // READ - Buscar por ID
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            Acessorio acessorio = acessorioService.findById(id);
            return new ResponseEntity<>(acessorio, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao buscar acessório: " + e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }
    }

    // READ - Listar todos
    @GetMapping("/findAll")
    public ResponseEntity<List<Acessorio>> findAll() {
        List<Acessorio> acessorios = acessorioService.findAll();
        return new ResponseEntity<>(acessorios, HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Acessorio acessorio) {
        try {
            String mensagem = acessorioService.update(id, acessorio);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar acessório: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            String mensagem = acessorioService.delete(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao deletar acessório: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
