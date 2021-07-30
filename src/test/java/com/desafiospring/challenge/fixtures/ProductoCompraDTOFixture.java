package com.desafiospring.challenge.fixtures;

import com.desafiospring.challenge.dtos.ProductPurchaseDTO;

import java.util.ArrayList;
import java.util.List;

public class ProductoCompraDTOFixture {

    // wrong purchase
    public static List<ProductPurchaseDTO> defaultWrongList() {
        List<ProductPurchaseDTO> productos = new ArrayList<>();
        productos.add(ProductoCompraDTOFixture.default2());
        productos.add(ProductoCompraDTOFixture.default3());
        return productos;
    }

    // successful purchase
    public static List<ProductPurchaseDTO> defaultSuccessfulList() {
        List<ProductPurchaseDTO> productos = new ArrayList<>();
        productos.add(ProductoCompraDTOFixture.default1());
        productos.add(ProductoCompraDTOFixture.default4());
        return productos;
    }

    public static ProductPurchaseDTO default1() {
        ProductPurchaseDTO producto = new ProductPurchaseDTO();
        producto.setProductId(1);
        producto.setName("Pelota");
        producto.setBrand("Adidas");
        producto.setQuantity(6);
        return producto;
    }

    public static ProductPurchaseDTO default2() {
        ProductPurchaseDTO producto = new ProductPurchaseDTO();
        producto.setProductId(2);
        producto.setName("Martillo");
        producto.setBrand("Acindar");
        producto.setQuantity(25);
        return producto;
    }

    public static ProductPurchaseDTO default3() {
        ProductPurchaseDTO producto = new ProductPurchaseDTO();
        producto.setProductId(3);
        producto.setName("Contenedor");
        producto.setBrand("Tenaris");
        producto.setQuantity(7);
        return producto;
    }

    public static ProductPurchaseDTO default4() {
        ProductPurchaseDTO producto = new ProductPurchaseDTO();
        producto.setProductId(4);
        producto.setName("Autito");
        producto.setBrand("Hot Wheels");
        producto.setQuantity(3);
        return producto;
    }
}
