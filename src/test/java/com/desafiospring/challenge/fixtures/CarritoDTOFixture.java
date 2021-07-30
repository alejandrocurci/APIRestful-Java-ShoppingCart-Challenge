package com.desafiospring.challenge.fixtures;

import com.desafiospring.challenge.dtos.CartDTO;

public class CarritoDTOFixture {

    // build an object CarritoDTO
    public static CartDTO crearCarrito() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setUserID(1);
        cartDTO.setTotalAcumulado(6*6000+3*4300);
        cartDTO.setArticulosEnCarrito(ProductoCompraDTOFixture.defaultSuccessfulList());
        return cartDTO;
    }
}
