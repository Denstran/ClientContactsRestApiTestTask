package org.example.clientcontactsrestapitesttask.repository;

import org.example.clientcontactsrestapitesttask.model.Contact;
import org.example.clientcontactsrestapitesttask.model.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByClient_Id(Long clientId);
    List<Contact> findAllByClient_IdAndType(Long clientId, ContactType contactType);
}
