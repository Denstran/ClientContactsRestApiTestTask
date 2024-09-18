package org.example.clientcontactsrestapitesttask.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Name {
    @NotNull(message = "Имя не может отсутствовать!")
    @NotEmpty(message = "Фамилия не может отсутствовать!")
    private String firstName;

    @NotNull(message = "Имя не может отсутствовать!")
    @NotEmpty(message = "Фамилия не может отсутствовать!")
    private String lastName;
}
