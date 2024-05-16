package com.lhc.backend.controllers;

import com.lhc.backend.common.DTO.client.ClientSaveDTO;
import com.lhc.backend.models.ClientModel;
import com.lhc.backend.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/client")
public class ClientController {

    ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("")
    public ResponseEntity<ClientModel> saveClient(@Validated @RequestBody ClientSaveDTO clientSaveDTO){
        return clientService.saveClient(clientSaveDTO);
    }
}
