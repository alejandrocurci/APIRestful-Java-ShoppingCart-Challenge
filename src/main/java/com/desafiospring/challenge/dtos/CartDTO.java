package com.desafiospring.challenge.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private int userID;
    private int totalAcumulado;
    private List<ProductPurchaseDTO> articulosEnCarrito;
}
