package com.desafiospring.challenge.fixtures;

import com.desafiospring.challenge.dtos.ProductDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDTOFixture {

    // method to load json files from /resources
    public static List<ProductDTO> loadJson(String path) {
        File file = null;
        try {
            file = ResourceUtils.getFile(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<ProductDTO>> typeReference = new TypeReference<List<ProductDTO>>() {
        };
        List<ProductDTO> productos = null;
        try {
            productos = objectMapper.readValue(file, typeReference);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productos;
    }

    public static List<ProductDTO> defaultOrderedList(int order) {
        List<ProductDTO> productos = new ArrayList<>();
        productos.add(ProductoDTOFixture.default1());
        productos.add(ProductoDTOFixture.default2());
        productos.add(ProductoDTOFixture.default3());
        productos.add(ProductoDTOFixture.default4());
        switch(order) {
            case 0:
                productos.sort((a,b) -> a.getName().compareTo(b.getName()));
                break;
            case 1:
                productos.sort((a,b) -> b.getName().compareTo(a.getName()));
                break;
            case 2:
                productos.sort((a,b) -> a.getPrice() - b.getPrice());
                break;
            case 3:
                productos.sort((a,b) -> b.getPrice() - a.getPrice());
                break;
            default:
                break;
        }
        return productos;
    }

    // list with tools which have free shipping
    public static List<ProductDTO> defaultFilteredList() {
        List<ProductDTO> productos = new ArrayList<>();
        productos.add(ProductoDTOFixture.default3());
        return productos;
    }

    // list only with tools
    public static List<ProductDTO> defaultToolsList() {
        List<ProductDTO> productos = new ArrayList<>();
        productos.add(ProductoDTOFixture.default2());
        productos.add(ProductoDTOFixture.default3());
        return productos;
    }

    // list with every item
    public static List<ProductDTO> defaultList() {
        List<ProductDTO> productos = new ArrayList<>();
        productos.add(ProductoDTOFixture.default1());
        productos.add(ProductoDTOFixture.default2());
        productos.add(ProductoDTOFixture.default3());
        productos.add(ProductoDTOFixture.default4());
        return productos;
    }

    public static ProductDTO default1() {
        ProductDTO producto = new ProductDTO();
        producto.setId(1);
        producto.setName("Pelota");
        producto.setBrand("Adidas");
        producto.setCategory("Deportes");
        producto.setPrestige("***");
        producto.setPrice(6000);
        producto.setFreeShipping(true);
        producto.setQuantity(15);
        return producto;
    }

    public static ProductDTO default2() {
        ProductDTO producto = new ProductDTO();
        producto.setId(2);
        producto.setName("Martillo");
        producto.setBrand("Acindar");
        producto.setCategory("Herramientas");
        producto.setPrestige("****");
        producto.setPrice(2700);
        producto.setFreeShipping(false);
        producto.setQuantity(8);
        return producto;
    }

    public static ProductDTO default3() {
        ProductDTO producto = new ProductDTO();
        producto.setId(3);
        producto.setName("Contenedor");
        producto.setBrand("Tenaris");
        producto.setCategory("Herramientas");
        producto.setPrestige("**");
        producto.setPrice(8100);
        producto.setFreeShipping(true);
        producto.setQuantity(2);
        return producto;
    }

    public static ProductDTO default4() {
        ProductDTO producto = new ProductDTO();
        producto.setId(4);
        producto.setName("Autito");
        producto.setBrand("Hot Wheels");
        producto.setCategory("Juguetes");
        producto.setPrestige("*****");
        producto.setPrice(4300);
        producto.setFreeShipping(true);
        producto.setQuantity(20);
        return producto;
    }
}
