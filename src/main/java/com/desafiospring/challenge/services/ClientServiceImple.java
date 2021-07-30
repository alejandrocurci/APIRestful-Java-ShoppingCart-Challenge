package com.desafiospring.challenge.services;

import com.desafiospring.challenge.dtos.ClientDTO;
import com.desafiospring.challenge.dtos.ClientDataDTO;
import com.desafiospring.challenge.dtos.StatusDTO;
import com.desafiospring.challenge.exceptions.ClientException;
import com.desafiospring.challenge.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClientServiceImple implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // create new client
    private ClientDataDTO createClient(ClientDTO data, String pass) throws ClientException {
        ClientDataDTO newClient = new ClientDataDTO();
        newClient.setId(data.getId());
        newClient.setName(data.getName());
        newClient.setEmail(data.getEmail());
        newClient.setCity(data.getCity());
        newClient.setPassword(pass);
        clientRepository.saveClient(newClient);
        return newClient;
    }

    // validates client credentials
    private void validateCredentials(ClientDTO data, String pass) throws ClientException {
        Map<Integer, ClientDataDTO> repository = clientRepository.loadClients();
        if (repository.containsKey(data.getId())) {
            throw new ClientException("El cliente ya se encuentra registrado");
        } else if (data.getId() < 0) {
            throw new ClientException("El dni es inválido");
        }
        if (!validatesEmail(data.getEmail())) {
            throw new ClientException("El email es inválido");
        }
        if (!validatesPassword(pass)) {
            throw new ClientException("La contraseña es inválida");
        }
        if (data.getCity().isEmpty()) {
            throw new ClientException("No se ha indicado la provincia del nuevo cliente");
        }
    }

    private boolean validatesEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern p = Pattern.compile(regex);
        if (email.isEmpty()) {
            return false;
        }
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private boolean validatesPassword(String pass) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,12}$"; // entre 6 y 12 caracteres, nummeros, mayusculas y minusculas
        Pattern p = Pattern.compile(regex);
        if (pass.isEmpty()) {
            return false;
        }
        Matcher m = p.matcher(pass);
        return m.matches();
    }

    // register validated client
    @Override
    public StatusDTO registerClient(ClientDTO data, String pass) throws ClientException {
        Map<Integer, ClientDataDTO> repositorio = clientRepository.loadClients();
        validateCredentials(data, pass);
        ClientDataDTO cliente = createClient(data, pass);
        return new StatusDTO(200, "El cliente " + data.getName() + " fue satisfactoriamente creado.");
    }

    // lists every registered client
    @Override
    public List<ClientDTO> listClients(String city) throws ClientException {
        List<ClientDTO> list = new ArrayList<>();
        Map<Integer, ClientDataDTO> repositorio = clientRepository.loadClients();
        for (Map.Entry<Integer, ClientDataDTO> entry : repositorio.entrySet()) {
            ClientDTO client = new ClientDTO();
            client.setId(entry.getKey());
            client.setName(entry.getValue().getName());
            client.setEmail(entry.getValue().getEmail());
            client.setCity(entry.getValue().getCity());
            list.add(client);
        }
        if (!city.isEmpty()) {
            list.removeIf(c -> !c.getCity().equalsIgnoreCase(city));
        }
        if (list.isEmpty()) {
            throw new ClientException("No hay clientes de la provincia " + city);
        }
        return list;
    }
}
