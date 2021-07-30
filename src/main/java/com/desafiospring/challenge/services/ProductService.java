package com.desafiospring.challenge.services;

import com.desafiospring.challenge.dtos.CartDTO;
import com.desafiospring.challenge.dtos.PayloadDTO;
import com.desafiospring.challenge.dtos.ProductDTO;
import com.desafiospring.challenge.dtos.ResponseDTO;
import com.desafiospring.challenge.exceptions.ProductException;
import com.desafiospring.challenge.exceptions.UserException;

import java.util.List;

public interface ProductService {

    public List<ProductDTO> listAvailableProducts(String category, String brand,
                                                  String priceStr, String freeShippingStr,
                                                  String prestige, String order) throws ProductException;

    public ResponseDTO createTicket(PayloadDTO payloadDTO) throws ProductException;

    public CartDTO addToCart(String userID, PayloadDTO payloadDTO) throws ProductException, UserException;

    public ResponseDTO purchaseCart(String userID) throws UserException, ProductException;
}
