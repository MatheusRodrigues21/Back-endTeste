package com.example.teste1.controller;

import com.example.teste1.entity.Locacao;
import com.example.teste1.service.LocacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locacao")
@CrossOrigin(origins = "*")
public class LocacaoController {

    @Autowired
    private LocacaoService locacaoService;

    // CREATE
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Locacao locacao) {
        try {
            String mensagem = locacaoService.save(locacao);
            return new ResponseEntity<>(mensagem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao salvar locação: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    // READ - Buscar por ID
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            Locacao locacao = locacaoService.findById(id);
            return new ResponseEntity<>(locacao, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao buscar locação: " + e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }
    }

    // READ - Listar todos
    @GetMapping("/findAll")
    public ResponseEntity<List<Locacao>> findAll() {
        List<Locacao> locacoes = locacaoService.findAll();
        return new ResponseEntity<>(locacoes, HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Locacao locacao) {
        try {
            String mensagem = locacaoService.update(id, locacao);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar locação: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            String mensagem = locacaoService.delete(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao deletar locação: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }
}