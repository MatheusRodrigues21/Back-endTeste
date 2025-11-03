package com.example.teste1.service;

import com.example.teste1.entity.Cliente;
import com.example.teste1.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // CREATE
    public String save(Cliente cliente) {
        clienteRepository.save(cliente);
        return "Cliente salvo com sucesso!";
    }

    // READ - Buscar por ID
    public Cliente findById(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));
    }

    // READ - Listar todos
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    // UPDATE
    public String update(Integer id, Cliente novoCliente) {
        Cliente existente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado para atualização"));

        existente.setCpf(novoCliente.getCpf());
        existente.setNome(novoCliente.getNome());
        existente.setTelefone(novoCliente.getTelefone());

        clienteRepository.save(existente);
        return "Cliente atualizado com sucesso!";
    }

    // DELETE
    public String delete(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado para exclusão");
        }
        clienteRepository.deleteById(id);
        return "Cliente deletado com sucesso!";
    }
}