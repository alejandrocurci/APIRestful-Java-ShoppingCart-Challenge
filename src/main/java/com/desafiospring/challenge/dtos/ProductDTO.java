package com.desafiospring.challenge.dtos;

import lombok.Data;

@Data
public class ProductDTO {
    private int id;
    private String name;
    private String category;
    private String brand;
    private int price;
    private int quantity;
    private boolean freeShipping;
    private String prestige;
}
