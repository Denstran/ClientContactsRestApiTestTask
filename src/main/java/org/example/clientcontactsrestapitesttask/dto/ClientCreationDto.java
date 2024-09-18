package org.example.clientcontactsrestapitesttask.dto;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.example.clientcontactsrestapitesttask.model.Client;
import org.example.clientcontactsrestapitesttask.model.Name;

@Getter
@Setter
public class ClientCreationDto {
    @Valid
    private Name name;

    public Client toClient() {
        return new Client(name);
    }
}
