package com.lhc.backend.services;

import com.lhc.backend.models.ClientModel;
import com.lhc.backend.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<ClientModel> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<ClientModel> getClientById(String id) {
        return clientRepository.findById(UUID.fromString(id));
    }

    public ClientModel createClient(ClientModel client) {
        return clientRepository.save(client);
    }

    public ClientModel updateClient(String id, ClientModel clientDetails) {
        ClientModel client = clientRepository.findById(UUID.fromString(id)).orElseThrow();
        client.setCpf(clientDetails.getCpf());
        client.setName(clientDetails.getName());
        return clientRepository.save(client);
    }

    public void deleteClient(String id) {
        clientRepository.deleteById(UUID.fromString(id));
    }
}
