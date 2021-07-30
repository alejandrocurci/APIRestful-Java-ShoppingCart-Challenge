package com.desafiospring.challenge.fixtures;

import com.desafiospring.challenge.dtos.StatusDTO;

public class StatusDTOFixture {

    // success status
    public static StatusDTO successStatus() {
        StatusDTO status = new StatusDTO(200, "La solicitud de compra se completó con éxito");
        return status;
    }
}
