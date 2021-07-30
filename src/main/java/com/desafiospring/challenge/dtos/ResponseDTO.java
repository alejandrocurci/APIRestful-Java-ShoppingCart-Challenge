package com.desafiospring.challenge.dtos;

import lombok.Data;

@Data
public class ResponseDTO {
    private TicketDTO ticket;
    private StatusDTO statusCode;
}
