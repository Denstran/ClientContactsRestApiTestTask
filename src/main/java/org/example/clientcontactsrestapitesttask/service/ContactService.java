package org.example.clientcontactsrestapitesttask.service;

import org.example.clientcontactsrestapitesttask.model.Contact;
import org.example.clientcontactsrestapitesttask.model.ContactType;
import org.example.clientcontactsrestapitesttask.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional
    public void createContact(Contact contact) {
        contactRepository.save(contact);
    }

    public List<Contact> getClientContacts(Long clientId, ContactType contactType) {
        if (contactType == null)
            return contactRepository.findAllByClient_Id(clientId);

        return contactRepository.findAllByClient_IdAndType(clientId, contactType);
    }
}
