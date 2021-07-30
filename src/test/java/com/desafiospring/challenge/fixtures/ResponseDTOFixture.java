package com.desafiospring.challenge.fixtures;

import com.desafiospring.challenge.dtos.ResponseDTO;

public class ResponseDTOFixture {

    public static ResponseDTO successResponse() {
        ResponseDTO response = new ResponseDTO();
        response.setTicket(TicketDTOFixture.successTicket());
        response.setStatusCode(StatusDTOFixture.successStatus());
        return response;
    }


}
