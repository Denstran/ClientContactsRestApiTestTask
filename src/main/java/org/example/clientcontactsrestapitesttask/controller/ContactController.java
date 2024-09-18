package org.example.clientcontactsrestapitesttask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.example.clientcontactsrestapitesttask.dto.ContactCreationDto;
import org.example.clientcontactsrestapitesttask.dto.ContactDto;
import org.example.clientcontactsrestapitesttask.dto.ValidationErrorResponse;
import org.example.clientcontactsrestapitesttask.model.Client;
import org.example.clientcontactsrestapitesttask.model.Contact;
import org.example.clientcontactsrestapitesttask.model.ContactType;
import org.example.clientcontactsrestapitesttask.service.ClientService;
import org.example.clientcontactsrestapitesttask.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients/{clientId}/contacts")
public class ContactController {
    private final ContactService contactService;
    private final ClientService clientService;

    public ContactController(ContactService contactService, ClientService clientService) {
        this.contactService = contactService;
        this.clientService = clientService;
    }

    @Operation(summary = "Получить контакты клиента",
            description = "Возвращает список контактов клиента. Можно указать тип контакта для фильтрации.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка контактов")
    })
    @GetMapping
    public ResponseEntity<List<ContactDto>> getClientContact(
            @Parameter(description = "ID клиента для получения контактов", example = "1")
            @PathVariable Long clientId,

            @Parameter(description = "Тип контакта для фильтрации (PHONE или EMAIL)", required = false,
                    example = "PHONE_NUMBER")
            @RequestParam(required = false) ContactType contactType) {

        List<ContactDto> contacts =
                ContactDto.getFromContactsList(contactService.getClientContacts(clientId, contactType));

        return ResponseEntity.ok(contacts);
    }

    @Operation(summary = "Создание нового контакта",
            description = "Создает новый контакт для клиента по его ID. " +
                    "Тип контакта (телефон или email) и значение должны быть указаны.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Контакт успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса или клиент не найден")
    })
    @PostMapping("/create")
    public ResponseEntity<?> createContact(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные для создания нового контакта")
            @Valid @RequestBody ContactCreationDto dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(ValidationErrorResponse.createValidationErrorResponse(bindingResult));
        }

        Optional<Client> clientOptional = clientService.getClientById(dto.getClientId());
        if (clientOptional.isEmpty())
            return ResponseEntity.badRequest().body(String.format("Client with id %d not found!", dto.getClientId()));

        Contact contact = new Contact(clientOptional.get(), dto.getContactType(), dto.getValue());
        contactService.createContact(contact);
        return ResponseEntity.ok("Contact created");
    }
}
