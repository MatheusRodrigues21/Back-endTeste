package com.example.teste1.controller;

import com.example.teste1.entity.Veiculo;
import com.example.teste1.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculo")
@CrossOrigin(origins = "*") // Permite requisições externas (ex: frontend Angular)
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    // CREATE
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Veiculo veiculo) {
        try {
            String mensagem = veiculoService.save(veiculo);
            return new ResponseEntity<>(mensagem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao salvar veículo: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    // READ - Buscar por ID
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Veiculo veiculo = veiculoService.findById(id);
            return new ResponseEntity<>(veiculo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao buscar veículo: " + e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }
    }

    // READ - Listar todos
    @GetMapping("/findAll")
    public ResponseEntity<List<Veiculo>> findAll() {
        List<Veiculo> veiculos = veiculoService.findAll();
        return new ResponseEntity<>(veiculos, HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Veiculo veiculo) {
        try {
            String mensagem = veiculoService.update(id, veiculo);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar veículo: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String mensagem = veiculoService.delete(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao deletar veículo: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
