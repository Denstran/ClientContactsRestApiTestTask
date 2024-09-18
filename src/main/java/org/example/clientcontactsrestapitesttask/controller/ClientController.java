package org.example.clientcontactsrestapitesttask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.example.clientcontactsrestapitesttask.dto.ClientCreationDto;
import org.example.clientcontactsrestapitesttask.dto.ClientDto;
import org.example.clientcontactsrestapitesttask.dto.ValidationErrorResponse;
import org.example.clientcontactsrestapitesttask.model.Client;
import org.example.clientcontactsrestapitesttask.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients() {
        List<ClientDto> clients = ClientDto.getFormClientList(clientService.getAllClients());
        return ResponseEntity.ok(clients);
    }

    @Operation(summary = "Получение клиента по ID",
            description = "Возвращает информацию о клиенте по его идентификатору. Если клиент не найден, возвращает null.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Клиент успешно найден"),
    })
    @GetMapping("/client/{clientId}")
    public ResponseEntity<ClientDto> getClientById(
            @Parameter(description = "Идентификатор клиента", required = true)
            @PathVariable Long clientId) {

        Optional<Client> clientOptional = clientService.getClientById(clientId);

        return clientOptional
                .map(client -> ResponseEntity.ok(ClientDto.getFromClient(client)))
                .orElseGet(() -> ResponseEntity.ok(null));
    }

    @Operation(summary = "Создание нового клиента",
            description = "Создает нового клиента с переданными данными. Если есть ошибки валидации, они будут возвращены в ответе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Клиент успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибки валидации в теле запроса")
    })
    @PostMapping("/create")
    public ResponseEntity<?> createClient(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные для создания нового клиента")
            @Valid @RequestBody ClientCreationDto dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(ValidationErrorResponse.createValidationErrorResponse(bindingResult));
        }

        clientService.createClient(dto.toClient());
        return ResponseEntity.ok("Client created");
    }

}
