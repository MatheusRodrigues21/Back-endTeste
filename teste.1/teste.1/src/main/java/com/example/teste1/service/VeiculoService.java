package com.example.teste1.service;

import com.example.teste1.entity.Veiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.teste1.repository.VeiculoRepository;

import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public String save (Veiculo veiculo){

        this.veiculoRepository.save(veiculo);

        return "Veiculo salvo com sucesso!";

    }

    public Veiculo findById (Long id){

      Optional<Veiculo> veiculo = this.veiculoRepository.findById(id);
       return veiculo.get();

    }

}
