package com.desafiospring.challenge.repositories;

import com.desafiospring.challenge.dtos.ClientDataDTO;
import com.desafiospring.challenge.exceptions.ClientException;

import java.util.Map;

public interface ClientRepository {
    public void saveClient(ClientDataDTO client) throws ClientException;

    public Map<Integer, ClientDataDTO> loadClients();
}
