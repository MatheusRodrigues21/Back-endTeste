package com.example.teste1.controller;

import com.example.teste1.entity.Veiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.teste1.service.VeiculoService;

@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @PostMapping ("/save")
    public ResponseEntity<String> save(@RequestBody Veiculo veiculo){
        try {

            String mensagem = this.veiculoService.save(veiculo);
            return new ResponseEntity<String>(mensagem, HttpStatus.OK);

        } catch (Exception e){

            return new ResponseEntity<String>("Deu algo errado ao salvar!", HttpStatus.BAD_REQUEST);

        }

    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Veiculo> findById(@PathVariable Long id){

        try {
            Veiculo veiculo =  this.veiculoService.findById(id);
            return new ResponseEntity<>(veiculo, HttpStatus.OK);

        } catch (Exception e){

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }
}
