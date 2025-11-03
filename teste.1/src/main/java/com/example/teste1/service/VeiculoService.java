package com.example.teste1.service;

import com.example.teste1.entity.Veiculo;
import com.example.teste1.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    // CREATE
    public String save(Veiculo veiculo) {
        veiculoRepository.save(veiculo);
        return "Veículo salvo com sucesso!";
    }

    // READ - Buscar por ID
    public Veiculo findById(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com o ID: " + id));
    }

    // READ - Listar todos
    public List<Veiculo> findAll() {
        return veiculoRepository.findAll();
    }

    // UPDATE
    public String update(Long id, Veiculo novoVeiculo) {
        Veiculo existente = veiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado para atualização"));

        existente.setModelo(novoVeiculo.getModelo());
        existente.setStatus(novoVeiculo.getStatus());

        veiculoRepository.save(existente);
        return "Veículo atualizado com sucesso!";
    }

    // DELETE
    public String delete(Long id) {
        if (!veiculoRepository.existsById(id)) {
            throw new RuntimeException("Veículo não encontrado para exclusão");
        }
        veiculoRepository.deleteById(id);
        return "Veículo deletado com sucesso!";
    }
}
