package com.desafiospring.challenge.dtos;

import lombok.Data;

@Data
public class ProductPurchaseDTO {
    private int productId;
    private String name;
    private String brand;
    private int quantity;
}
