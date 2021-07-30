package com.desafiospring.challenge.controllers;

import com.desafiospring.challenge.dtos.PayloadDTO;
import com.desafiospring.challenge.dtos.StatusDTO;
import com.desafiospring.challenge.exceptions.ProductException;
import com.desafiospring.challenge.exceptions.UserException;
import com.desafiospring.challenge.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/articles")
    public ResponseEntity listProducts(@RequestParam(required = false, defaultValue = "") String category,
                                       @RequestParam(required = false, defaultValue = "") String brand,
                                       @RequestParam(required = false, defaultValue = "") String price,
                                       @RequestParam(required = false, defaultValue = "") String freeShipping,
                                       @RequestParam(required = false, defaultValue = "") String prestige,
                                       @RequestParam(required = false, defaultValue = "") String order) throws ProductException {
        return new ResponseEntity(productService.listAvailableProducts(category, brand, price, freeShipping, prestige, order), HttpStatus.OK);
    }

    @PostMapping("/purchase-request")
    public ResponseEntity requestPurchase(@RequestBody PayloadDTO payload) throws ProductException {
        return new ResponseEntity(productService.createTicket(payload), HttpStatus.OK);
    }

    @PostMapping("/cart")
    public ResponseEntity add(@RequestParam(required = false, defaultValue = "") String userID, @RequestBody(required = false) PayloadDTO payload) throws ProductException, UserException {
        return new ResponseEntity(productService.addToCart(userID, payload), HttpStatus.OK);
    }

    @PostMapping("/cart/purchase")
    public ResponseEntity purchase(@RequestParam(required = false, defaultValue = "") String userID) throws ProductException, UserException {
        return new ResponseEntity(productService.purchaseCart(userID), HttpStatus.OK);
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity handleProductExcepcion(ProductException e) {
        return new ResponseEntity(new StatusDTO(400, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity handleUserExcepcion(UserException e) {
        return new ResponseEntity(new StatusDTO(400, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
