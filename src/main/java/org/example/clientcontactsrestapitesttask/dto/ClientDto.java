package org.example.clientcontactsrestapitesttask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.clientcontactsrestapitesttask.model.Client;
import org.example.clientcontactsrestapitesttask.model.Name;

import java.util.List;

@AllArgsConstructor
@Data
public class ClientDto {
    private Long id;
    private Name name;

    public static ClientDto getFromClient(Client client) {
        return new ClientDto(client.getId(), client.getName());
    }

    public static List<ClientDto> getFormClientList(List<Client> clients) {
        return clients.stream()
                .map(client -> new ClientDto(client.getId(), client.getName()))
                .toList();
    }
}
