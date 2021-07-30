package com.desafiospring.challenge.fixtures;

import com.desafiospring.challenge.dtos.PayloadDTO;

public class PayloadDTOFixture {

    // successful payload
    public static PayloadDTO successPayload() {
        PayloadDTO payload = new PayloadDTO();
        payload.setArticles(ProductoCompraDTOFixture.defaultSuccessfulList());
        return payload;
    }

    // wrong payload
    public static PayloadDTO wrongPayload() {
        PayloadDTO payload = new PayloadDTO();
        payload.setArticles(ProductoCompraDTOFixture.defaultWrongList());
        return payload;
    }
}
