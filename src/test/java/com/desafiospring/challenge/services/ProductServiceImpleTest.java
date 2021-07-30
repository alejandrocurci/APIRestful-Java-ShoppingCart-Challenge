package com.desafiospring.challenge.services;

import com.desafiospring.challenge.dtos.CartDTO;
import com.desafiospring.challenge.dtos.ProductDTO;
import com.desafiospring.challenge.dtos.ResponseDTO;
import com.desafiospring.challenge.exceptions.ProductException;
import com.desafiospring.challenge.exceptions.UserException;
import com.desafiospring.challenge.fixtures.CarritoDTOFixture;
import com.desafiospring.challenge.fixtures.PayloadDTOFixture;
import com.desafiospring.challenge.fixtures.ProductoDTOFixture;
import com.desafiospring.challenge.fixtures.ResponseDTOFixture;
import com.desafiospring.challenge.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImpleTest {

    private ProductServiceImple service;
    private List<ProductDTO> defaultList;

    @Mock
    private ProductRepository repositoryMock;

    @BeforeEach
    void setUp() {
        service = new ProductServiceImple(repositoryMock);
        defaultList = ProductoDTOFixture.defaultList();
    }

    @Test
    @DisplayName("Get all articles")
    void getAllItems() throws ProductException {
        when(repositoryMock.buildRepository()).thenReturn(defaultList);
        List<ProductDTO> response = service.listAvailableProducts("", "", "",
                "", "", "");
        assertEquals(defaultList, response);
        assertEquals(4, response.size());
    }

    @Test
    @DisplayName("Exception when filtering with too many parameters")
    void handleTooManyFilters() {
        assertThrows(ProductException.class,
                () -> service.listAvailableProducts("Herramientas", "***", "10000",
                        "false", "", ""));
    }

    @Test
    @DisplayName("Filter items by category")
    void getToolsItems() throws ProductException {
        when(repositoryMock.buildRepository()).thenReturn(defaultList);
        List<ProductDTO> response = service.listAvailableProducts("Herramientas", "", "",
                "", "", "");
        assertEquals(ProductoDTOFixture.defaultToolsList(), response);
    }

    @Test
    @DisplayName("Filter by two parameters")
    void getFilteredItems() throws ProductException {
        when(repositoryMock.buildRepository()).thenReturn(defaultList);

        List<ProductDTO> response = service.listAvailableProducts("Herramientas", "", "",
                "true", "", "");

        assertEquals(ProductoDTOFixture.defaultFilteredList(), response);
    }

    @Test
    @DisplayName("Alphabetical order (asc)")
    void getAlphabeticalOrder() throws ProductException {
        when(repositoryMock.buildRepository()).thenReturn(defaultList);
        List<ProductDTO> response = service.listAvailableProducts("", "", "",
                "", "", "0");
        assertEquals(ProductoDTOFixture.defaultOrderedList(0), response);
    }

    @Test
    @DisplayName("Alphabetical order (desc)")
    void getReversedAlphabeticalOrder() throws ProductException {
        when(repositoryMock.buildRepository()).thenReturn(defaultList);
        List<ProductDTO> response = service.listAvailableProducts("", "", "",
                "", "", "1");
        assertEquals(ProductoDTOFixture.defaultOrderedList(1), response);
    }

    @Test
    @DisplayName("Price order (asc)")
    void getPriceOrder() throws ProductException {
        when(repositoryMock.buildRepository()).thenReturn(defaultList);
        List<ProductDTO> response = service.listAvailableProducts("", "", "",
                "", "", "2");
        assertEquals(ProductoDTOFixture.defaultOrderedList(2), response);
    }

    @Test
    @DisplayName("Price order (desc)")
    void getReversedPriceOrder() throws ProductException {
        when(repositoryMock.buildRepository()).thenReturn(defaultList);
        List<ProductDTO> response = service.listAvailableProducts("", "", "",
                "", "", "3");
        assertEquals(ProductoDTOFixture.defaultOrderedList(3), response);
    }

    // TESTS REGARDING generarTicket() METHOD

    @Test
    @DisplayName("Success ticket generation")
    void successPurchase() throws ProductException {
        when(repositoryMock.buildRepository()).thenReturn(defaultList);
        ResponseDTO actual = service.createTicket(PayloadDTOFixture.successPayload());
        ResponseDTO expected = ResponseDTOFixture.successResponse();

        assertEquals(expected.getTicket().getArticles(), actual.getTicket().getArticles());
        assertEquals(expected.getTicket().getTotal(), actual.getTicket().getTotal());
        assertEquals(expected.getStatusCode().getCode(), actual.getStatusCode().getCode());
    }

    @Test
    @DisplayName("Wrong purchase request")
    void wrongPurchase() {
        when(repositoryMock.buildRepository()).thenReturn(defaultList);

        assertThrows(ProductException.class,
                () -> service.createTicket(PayloadDTOFixture.wrongPayload()));
    }

    // TESTS REGARDING agregarAlCarrito() METHOD

    @Test
    @DisplayName("Add items to shopping chart")
    void addToChart() throws UserException, ProductException {
        when(repositoryMock.buildRepository()).thenReturn(defaultList);
        CartDTO actual = service.addToCart("1", PayloadDTOFixture.successPayload());
        CartDTO expected = CarritoDTOFixture.crearCarrito();

        assertEquals(actual.getArticulosEnCarrito(), expected.getArticulosEnCarrito());
        assertEquals(actual.getTotalAcumulado(), expected.getTotalAcumulado());
    }

    @Test
    @DisplayName("Buy items in shopping chart")
    void buyChart() throws ProductException, UserException {
        when(repositoryMock.buildRepository()).thenReturn(defaultList);
        CartDTO currentChart = service.addToCart("1", PayloadDTOFixture.successPayload());
        ResponseDTO actual = service.purchaseCart("1");
        ResponseDTO expected = ResponseDTOFixture.successResponse();

        assertEquals(actual.getTicket().getTotal(), expected.getTicket().getTotal());
        assertEquals(actual.getTicket().getArticles(), expected.getTicket().getArticles());
    }

    @Test
    @DisplayName("No items in shopping chart")
    void buyEmptyChart() {

        assertThrows(UserException.class,
                () -> service.purchaseCart("1"));
    }

}