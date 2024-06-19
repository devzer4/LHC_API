package com.lhc.backend.repositories;

import com.lhc.backend.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientModel, UUID> {

    ClientModel findByCpf(String cpf);
}
