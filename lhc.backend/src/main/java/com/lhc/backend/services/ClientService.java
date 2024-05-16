package com.lhc.backend.services;

import com.lhc.backend.common.DTO.client.ClientSaveDTO;
import com.lhc.backend.models.ClientModel;
import com.lhc.backend.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public ResponseEntity<ClientModel> saveClient(ClientSaveDTO clientSaveDTO) {
        ClientModel clienteSalvo = new ClientModel();
        clienteSalvo.setCpf(clientSaveDTO.getCpf());
        clienteSalvo.setName(clientSaveDTO.getName());

        clientRepository.save(clienteSalvo);

        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(clienteSalvo);
    }
}
