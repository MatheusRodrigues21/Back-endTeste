package com.example.teste1.service;

import com.example.teste1.entity.Acessorio;
import com.example.teste1.repository.AcessorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcessorioService {

    @Autowired
    private AcessorioRepository acessorioRepository;

    // CREATE
    public String save(Acessorio acessorio) {
        acessorioRepository.save(acessorio);
        return "Acessório salvo com sucesso!";
    }

    // READ - Buscar por ID
    public Acessorio findById(Integer id) {
        return acessorioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Acessório não encontrado com o ID: " + id));
    }

    // READ - Listar todos
    public List<Acessorio> findAll() {
        return acessorioRepository.findAll();
    }

    // UPDATE
    public String update(Integer id, Acessorio novoAcessorio) {
        Acessorio existente = acessorioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Acessório não encontrado para atualização"));

        existente.setNome(novoAcessorio.getNome());
        existente.setTaxa_hora(novoAcessorio.getTaxa_hora());

        acessorioRepository.save(existente);
        return "Acessório atualizado com sucesso!";
    }

    // DELETE
    public String delete(Integer id) {
        if (!acessorioRepository.existsById(id)) {
            throw new RuntimeException("Acessório não encontrado para exclusão");
        }
        acessorioRepository.deleteById(id);
        return "Acessório deletado com sucesso!";
    }
}