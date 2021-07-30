package com.desafiospring.challenge.controllers;

import com.desafiospring.challenge.dtos.ClientDTO;
import com.desafiospring.challenge.dtos.StatusDTO;
import com.desafiospring.challenge.exceptions.ClientException;
import com.desafiospring.challenge.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/client-create")
    public ResponseEntity createClient(@RequestHeader String password, @RequestBody ClientDTO client) throws ClientException {
        return new ResponseEntity(clientService.registerClient(client, password), HttpStatus.OK);
    }

    @GetMapping("/client-list")
    public ResponseEntity listClients(@RequestParam(required = false, defaultValue = "") String city) throws ClientException {
        return new ResponseEntity(clientService.listClients(city), HttpStatus.OK);
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity handleUserExcepcion(ClientException e) {
        return new ResponseEntity(new StatusDTO(400, e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
