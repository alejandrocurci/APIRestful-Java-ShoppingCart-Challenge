package com.desafiospring.challenge.repositories;

import com.desafiospring.challenge.dtos.ProductDTO;
import com.desafiospring.challenge.dtos.ProductStringDTO;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryImple implements ProductRepository {

    // loads the CSV file into a list of articles (String format for every field)
    private List<ProductStringDTO> cargarRepositorio() {
        BufferedReader bufferedReader = null;
        List<ProductStringDTO> products = new ArrayList<>();
        int fila = 0;
        try {
            bufferedReader = new BufferedReader(new FileReader("src/main/resources/dbProductos.csv"));
            String line = bufferedReader.readLine();

            while (line != null) {
                String[] cells = line.split(",");
                // salteo la primer fila (títulos)
                if(fila>0) {
                    ProductStringDTO productDto = new ProductStringDTO();
                    productDto.setId(cells[0]);
                    productDto.setName(cells[1]);
                    productDto.setCategory(cells[2]);
                    productDto.setBrand(cells[3]);
                    productDto.setPrice(cells[4]);
                    productDto.setQuantity(cells[5]);
                    productDto.setFreeShipping(cells[6]);
                    productDto.setPrestige(cells[7]);
                    products.add(productDto);
                }

                line = bufferedReader.readLine();
                fila++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return products;
    }

    // formats every ProductStringDTO into ProductDTO
    @Override
    public List<ProductDTO> buildRepository() {
        List<ProductStringDTO> productStringDTOS = cargarRepositorio();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(ProductStringDTO p : productStringDTOS) {
            ProductDTO productDTO = new ProductDTO();
            try {
                productDTO.setId(Integer.parseInt(p.getId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            productDTO.setName(p.getName());
            productDTO.setCategory(p.getCategory());
            productDTO.setBrand(p.getBrand());
            try {
                String number = p.getPrice();
                number = number.replaceAll("\\.","").replaceAll("\\$","");
                productDTO.setPrice(Integer.parseInt(number));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                productDTO.setQuantity(Integer.parseInt(p.getQuantity()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(p.getFreeShipping().equals("SI")) {
                productDTO.setFreeShipping(true);
            } else {
                productDTO.setFreeShipping(false);
            }
            productDTO.setPrestige(p.getPrestige());
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }
}
