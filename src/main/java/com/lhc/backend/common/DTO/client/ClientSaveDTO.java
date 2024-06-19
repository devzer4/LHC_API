package com.lhc.backend.common.DTO.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientSaveDTO {
    private String cpf;
    private String name;
}
