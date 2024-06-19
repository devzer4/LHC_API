package com.lhc.backend.common.DTO.client;

import java.time.LocalDateTime;
import java.util.UUID;

public class ClientGetDTO {
    private UUID id;
    private String cpf;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
