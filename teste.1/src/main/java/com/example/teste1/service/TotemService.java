package com.example.teste1.service;

import com.example.teste1.entity.Totem;
import com.example.teste1.repository.TotemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TotemService {

    @Autowired
    private TotemRepository totemRepository;

    // CREATE
    public String save(Totem totem) {
        totemRepository.save(totem);
        return "Totem salvo com sucesso!";
    }

    // READ - Buscar por ID
    public Totem findById(Integer id) {
        return totemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Totem não encontrado com o ID: " + id));
    }

    // READ - Listar todos
    public List<Totem> findAll() {
        return totemRepository.findAll();
    }

    // UPDATE
    public String update(Integer id, Totem novoTotem) {
        Totem existente = totemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Totem não encontrado para atualização"));

        existente.setCodigo(novoTotem.getCodigo());
        existente.setLocal(novoTotem.getLocal());

        totemRepository.save(existente);
        return "Totem atualizado com sucesso!";
    }

    // DELETE
    public String delete(Integer id) {
        if (!totemRepository.existsById(id)) {
            throw new RuntimeException("Totem não encontrado para exclusão");
        }
        totemRepository.deleteById(id);
        return "Totem deletado com sucesso!";
    }
}