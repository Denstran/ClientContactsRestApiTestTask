package org.example.clientcontactsrestapitesttask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.clientcontactsrestapitesttask.model.Contact;
import org.example.clientcontactsrestapitesttask.model.ContactType;

import java.util.List;

@AllArgsConstructor
@Data
public class ContactDto {
    private Long id;
    private Long clientId;
    private ContactType contactType;
    private String value;

    public static ContactDto getFromContact(Contact contact) {
        return new ContactDto(contact.getId(), contact.getClient().getId(), contact.getType(), contact.getValue());
    }

    public static List<ContactDto> getFromContactsList(List<Contact> contacts) {
        return contacts.stream()
                .map(contact -> new ContactDto(
                        contact.getId(), contact.getClient().getId(), contact.getType(), contact.getValue()))
                .toList();
    }
}
