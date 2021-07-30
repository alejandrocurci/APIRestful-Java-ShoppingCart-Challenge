package com.desafiospring.challenge.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PayloadDTO {
    private List<ProductPurchaseDTO> articles;
}
