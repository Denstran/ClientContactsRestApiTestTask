package org.example.clientcontactsrestapitesttask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.clientcontactsrestapitesttask.model.ContactType;

@AllArgsConstructor
@Data
public class ContactCreationDto {
    private Long clientId;
    private ContactType contactType;
    private String value;
}
