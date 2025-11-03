package com.example.teste1.service;

import com.example.teste1.entity.Locacao;
import com.example.teste1.repository.LocacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocacaoService {

    @Autowired
    private LocacaoRepository locacaoRepository;

    // CREATE
    public String save(Locacao locacao) {
        locacaoRepository.save(locacao);
        return "Locação salva com sucesso!";
    }

    // READ - Buscar por ID
    public Locacao findById(Integer id) {
        return locacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locação não encontrada com o ID: " + id));
    }

    // READ - Listar todos
    public List<Locacao> findAll() {
        return locacaoRepository.findAll();
    }

    // UPDATE
    public String update(Integer id, Locacao novaLocacao) {
        Locacao existente = locacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locação não encontrada para atualização"));

        existente.setData_fim(novaLocacao.getData_fim());
        existente.setData_inicio(novaLocacao.getData_inicio());
        existente.setHoras(novaLocacao.getHoras());
        existente.setStatus(novaLocacao.getStatus());
        existente.setValor_total(novaLocacao.getValor_total());
        existente.setCliente_id(novaLocacao.getCliente_id());
        existente.setTotem_id(novaLocacao.getTotem_id());

        locacaoRepository.save(existente);
        return "Locação atualizada com sucesso!";
    }

    // DELETE
    public String delete(Integer id) {
        if (!locacaoRepository.existsById(id)) {
            throw new RuntimeException("Locação não encontrada para exclusão");
        }
        locacaoRepository.deleteById(id);
        return "Locação deletada com sucesso!";
    }
}