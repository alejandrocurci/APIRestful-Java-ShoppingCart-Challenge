package com.desafiospring.challenge.dtos;

import lombok.Data;

@Data
public class ClientDataDTO {
    private int id;
    private String name;
    private String email;
    private String password;
    private String city;
}
