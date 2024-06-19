package com.lhc.backend.controllers;

import com.lhc.backend.models.ClientModel;
import com.lhc.backend.models.UserModel;
import com.lhc.backend.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<ClientModel> getAllClients() {
        return clientService.getAllClients();
    }

    @PostMapping("/login")
    public ResponseEntity<ClientModel> loginClient(@RequestBody ClientModel client) {
        ClientModel clientFound = clientService.loginClient(client.getCpf());

        if(clientFound != null){
            return ResponseEntity.ok(clientFound);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientModel> getClientById(@PathVariable String id) {
        Optional<ClientModel> client = clientService.getClientById(id);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ClientModel createClient(@RequestBody ClientModel client) {
        return clientService.createClient(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientModel> updateClient(@PathVariable String id, @RequestBody ClientModel clientDetails) {
        ClientModel updatedClient = clientService.updateClient(id, clientDetails);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable String id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
