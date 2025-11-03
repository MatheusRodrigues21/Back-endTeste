package com.example.teste1.controller;

import com.example.teste1.entity.Totem;
import com.example.teste1.service.TotemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/totem")
@CrossOrigin(origins = "*")
public class TotemController {

    @Autowired
    private TotemService totemService;

    // CREATE
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Totem totem) {
        try {
            String mensagem = totemService.save(totem);
            return new ResponseEntity<>(mensagem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao salvar totem: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    // READ - Buscar por ID
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            Totem totem = totemService.findById(id);
            return new ResponseEntity<>(totem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao buscar totem: " + e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }
    }

    // READ - Listar todos
    @GetMapping("/findAll")
    public ResponseEntity<List<Totem>> findAll() {
        List<Totem> totems = totemService.findAll();
        return new ResponseEntity<>(totems, HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Totem totem) {
        try {
            String mensagem = totemService.update(id, totem);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar totem: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            String mensagem = totemService.delete(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao deletar totem: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }
}