package com.desafiospring.challenge.services;

import com.desafiospring.challenge.dtos.ClientDTO;
import com.desafiospring.challenge.dtos.StatusDTO;
import com.desafiospring.challenge.exceptions.ClientException;

import java.util.List;

public interface ClientService {
    public StatusDTO registerClient(ClientDTO data, String pass) throws ClientException;

    public List<ClientDTO> listClients(String city) throws ClientException;
}
