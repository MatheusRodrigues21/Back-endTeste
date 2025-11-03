package com.example.teste1.repository;

import com.example.teste1.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    // Agora o ID é Long, compatível com Veiculo
}
