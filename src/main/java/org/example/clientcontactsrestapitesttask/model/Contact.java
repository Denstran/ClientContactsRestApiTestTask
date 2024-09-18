package org.example.clientcontactsrestapitesttask.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CONTACT")
@NoArgsConstructor
@Getter
@Setter
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_CLIENT_CONTACT"))
    private Client client;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Тип не может отсутствовать")
    private ContactType type;

    @NotNull(message = "Значение контакта не может отсутствовать!")
    @NotEmpty(message = "Значение контакта не может отсутствовать!")
    private String value;

    public Contact(Client client, ContactType type, String value) {
        this.client = client;
        this.type = type;
        this.value = value;
    }
}
