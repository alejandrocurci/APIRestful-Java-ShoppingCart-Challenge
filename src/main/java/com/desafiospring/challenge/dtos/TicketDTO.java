package com.desafiospring.challenge.dtos;

import lombok.Data;

import java.util.List;

@Data
public class TicketDTO {
    private int id;
    private List<ProductPurchaseDTO> articles;
    private int total;
}
