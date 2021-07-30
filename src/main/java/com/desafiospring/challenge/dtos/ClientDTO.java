package com.desafiospring.challenge.dtos;

import lombok.Data;

@Data
public class ClientDTO {
    private int id;
    private String name;
    private String email;
    private String city;
}
