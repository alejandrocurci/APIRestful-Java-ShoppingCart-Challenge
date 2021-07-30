package com.desafiospring.challenge.services;

import com.desafiospring.challenge.dtos.*;
import com.desafiospring.challenge.exceptions.ProductException;
import com.desafiospring.challenge.exceptions.UserException;
import com.desafiospring.challenge.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImple implements ProductService {

    private ProductRepository repository;

    private int ticketID;
    private Map<Integer, List<ProductPurchaseDTO>> cart;

    public ProductServiceImple(ProductRepository repository) {
        ticketID = 1;
        cart = new HashMap<>();
        this.repository = repository;
    }

    // método para controlar la cantidad de parámetros recibidos en el request
    private int controlInput(String category, String brand, String price, String freeShipping, String prestige) {
        String params[] = {category, brand, price, freeShipping, prestige};
        int input = 0;
        for (String s : params) {
            if (s.equals("")) {
                input++;
            }
        }
        return input;
    }

    // método para listar todos los productos disponibles
    private List<ProductDTO> listProductsFromRepository() {
        return repository.buildRepository();
    }

    // método para listar los productos filtrados (con control en caso de no encontrar ningún producto con esa característica)
    private List<ProductDTO> filterProducts(String category, String brand, String priceStr, String freeShippingStr, String prestige) throws ProductException {
        List<ProductDTO> productos = repository.buildRepository();
        if (!category.isEmpty()) {
            productos.removeIf(p -> !p.getCategory().equalsIgnoreCase(category));
            if (productos.isEmpty()) {
                throw new ProductException("No se han encontrado productos de la categoria " + category);
            }
        }
        if (!brand.isEmpty()) {
            productos.removeIf(p -> !p.getBrand().equalsIgnoreCase(brand));
            if (productos.isEmpty()) {
                throw new ProductException("No se han encontrado productos de la marca " + brand);
            }
        }
        if (!priceStr.isEmpty()) {
            int price = Integer.parseInt(priceStr);
            productos.removeIf(p -> (p.getPrice() > price));
            if (productos.isEmpty()) {
                throw new ProductException("No se han encontrado productos con precio menor o igual a " + price);
            }
        }
        if (!prestige.isEmpty()) {
            productos.removeIf(p -> !p.getPrestige().equalsIgnoreCase(prestige));
            if (productos.isEmpty()) {
                throw new ProductException("No se han encontrado productos con prestigio " + prestige);
            }
        }
        if (!freeShippingStr.isEmpty()) {
            if (freeShippingStr.equalsIgnoreCase("false")) {
                productos.removeIf(p -> p.isFreeShipping() == true);
            } else {
                productos.removeIf(p -> p.isFreeShipping() == false);
            }
            if (productos.isEmpty()) {
                if (freeShippingStr.equalsIgnoreCase("true")) {
                    throw new ProductException("No se han encontrado productos con envio gratis");
                } else {
                    throw new ProductException("No se han encontrado productos sin envio gratis");
                }
            }
        }
        return productos;
    }

    // método para ordenar la lista de productos según el tipo de ordenamiento
    private void orderProducts(List<ProductDTO> lista, String ordenamiento) throws ProductException {
        if (!ordenamiento.isEmpty()) {
            int ord = Integer.parseInt(ordenamiento);
            switch (ord) {
                case 0:
                    // ordeno por nombre ascendete
                    lista.sort((p1, p2) -> (p1.getName().compareTo(p2.getName())));
                    break;
                case 1:
                    // ordeno por nombre descendente
                    lista.sort((p1, p2) -> (p2.getName().compareTo(p1.getName())));
                    break;
                case 2:
                    // ordeno por precio ascendente
                    lista.sort((p1, p2) -> (p1.getPrice() - p2.getPrice()));
                    break;
                case 3:
                    // ordeno por precio descendente
                    lista.sort((p1, p2) -> (p2.getPrice() - p1.getPrice()));
                    break;
                default:
                    throw new ProductException("No se ha podido ordenar la lista");
            }
        }

    }

    // método que procesa la GET request con o sin parámetros (llama a todas las funciones anteriores)
    @Override
    public List<ProductDTO> listAvailableProducts(String category, String brand, String priceStr, String freeShippingStr, String prestige, String order) throws ProductException {
        int count = controlInput(category, brand, priceStr, freeShippingStr, prestige);
        // si hay más de 2 parámetros, largo una excepción
        if (count < 3) {
            throw new ProductException("No se puede filtrar por más de 2 parámetros");
        }
        // si no recibo parámetros, listo todos los productos disponibles
        if (count == 5) {
            List<ProductDTO> listado = listProductsFromRepository();
            orderProducts(listado, order);
            return listado;
            // si recibo 1 o 2 parámetros, filtro lo que corresponda
        } else {
            List<ProductDTO> listado = filterProducts(category, brand, priceStr, freeShippingStr, prestige);
            orderProducts(listado, order);
            return listado;
        }
    }

    // Genero el objeto de respuesta de la api (ticket + status)
    @Override
    public ResponseDTO createTicket(PayloadDTO payloadDTO) throws ProductException {
        verifyPurchase(payloadDTO);
        TicketDTO ticket = new TicketDTO();
        ticket.setId(ticketID);
        ticketID++;
        ticket.setArticles(payloadDTO.getArticles());
        ticket.setTotal(calculatePrice(payloadDTO.getArticles()));
        StatusDTO statusDto = new StatusDTO(200, "La solicitud de compra se completó con éxito");
        ResponseDTO responseTicket = new ResponseDTO();
        responseTicket.setTicket(ticket);
        responseTicket.setStatusCode(statusDto);
        return responseTicket;
    }

    // método para verificar los articulos de la compra
    // (compruebo ID, nombre, marca y stock)
    private void verifyPurchase(PayloadDTO compra) throws ProductException {
        List<ProductDTO> productos = repository.buildRepository();
        List<ProductPurchaseDTO> listaCompra = compra.getArticles();
        for (ProductPurchaseDTO articulo : listaCompra) {
            ProductDTO item = null;
            Optional<ProductDTO> itemOption = productos.stream()
                    .filter(p -> p.getId() == articulo.getProductId())
                    .findFirst();
            if (itemOption.isPresent()) {
                item = itemOption.get();
            } else {
                throw new ProductException("No se ha encontrado un producto con ese ID");
            }
            if (!(item.getName().equalsIgnoreCase(articulo.getName()))) {
                throw new ProductException("El nombre " + articulo.getName() + " del producto de ID=" + articulo.getProductId() + " es incorrecto");
            }
            if (!(item.getBrand().equalsIgnoreCase(articulo.getBrand()))) {
                throw new ProductException("La marca " + articulo.getBrand() + " del producto de ID=" + articulo.getProductId() + " es incorrecta");
            }
            if (item.getQuantity() < articulo.getQuantity()) {
                throw new ProductException("No hay suficiente stock del producto '" + item.getName() + "'. Quedan " + item.getQuantity() + " items.");
            }
        }
    }

    // método para calcular el precio total de un listado de productos recibidos
    private int calculatePrice(List<ProductPurchaseDTO> itemsCompra) throws ProductException {
        int total = 0;
        List<ProductDTO> itemsStock = repository.buildRepository();
        for (ProductPurchaseDTO articulo : itemsCompra) {
            ProductDTO item;
            Optional<ProductDTO> itemOption = itemsStock.stream()
                    .filter(p -> p.getId() == articulo.getProductId())
                    .findFirst();
            if (itemOption.isPresent()) {
                item = itemOption.get();
            } else {
                throw new ProductException("No se ha encontrado un producto con ese ID");
            }
            total += articulo.getQuantity() * item.getPrice();
        }
        return total;
    }

    // método para agregar un listado de items al carrito
    @Override
    public CartDTO addToCart(String userIdStr, PayloadDTO payloadDTO) throws ProductException, UserException {
        int userID;
        if(!userIdStr.isEmpty()) {
            userID = Integer.parseInt(userIdStr);
        } else {
            throw new UserException("Debe proveerse un id para agregar articulos al carrito");
        }
        if (payloadDTO != null) {
            verifyPurchase(payloadDTO);
            if (cart.containsKey(userID)) {
                // usuario existente, agrego articulos nuevos y actualizo la cantidad de los ya presentes en el carrito
                List<ProductPurchaseDTO> productosEnCarrito = cart.get(userID);
                for (ProductPurchaseDTO articulo : payloadDTO.getArticles()) {
                    Optional<ProductPurchaseDTO> itemOption = productosEnCarrito.stream()
                            .filter(p -> p.getProductId() == articulo.getProductId())
                            .findFirst();
                    Optional<ProductDTO> itemStock = repository.buildRepository().stream()
                            .filter(p -> p.getId() == articulo.getProductId())
                            .findFirst();
                    if (itemOption.isPresent()) {
                        ProductPurchaseDTO item = itemOption.get();
                        ProductDTO stock = itemStock.get();
                        if ((item.getQuantity() + articulo.getQuantity()) > stock.getQuantity()) {
                            throw new ProductException("No hay suficiente stock del artículo " + stock.getName());
                        }
                        item.setQuantity(item.getQuantity() + articulo.getQuantity());
                    } else {
                        productosEnCarrito.add(articulo);
                    }
                }
            } else {
                // usuario nuevo, agrego todos los articulos al carrito
                cart.put(userID, new ArrayList<>());
                cart.get(userID).addAll(payloadDTO.getArticles());
            }
        }
        return showCart(userID);
    }

    // método para mostrar los items presentes en el carrito de un usuario
    private CartDTO showCart(int userID) throws ProductException, UserException {
        if (!cart.containsKey(userID)) {
            throw new UserException("'userID' incorrecto. El usuario no existe");
        }
        List<ProductPurchaseDTO> listado = cart.get(userID);
        CartDTO cartDTO = new CartDTO();
        cartDTO.setUserID(userID);
        cartDTO.setArticulosEnCarrito(listado);
        cartDTO.setTotalAcumulado(calculatePrice(listado));
        return cartDTO;
    }

    // método para crear el ticket al comprar los articulos del carrito
    @Override
    public ResponseDTO purchaseCart(String userIdStr) throws UserException, ProductException {
        int userID;
        if(!userIdStr.isEmpty()) {
            userID = Integer.parseInt(userIdStr);
        } else {
            throw new UserException("Debe proveerse un id para comprar los articulos del carrito");
        }
        if (!cart.containsKey(userID)) {
            throw new UserException("'userID' incorrecto. El usuario no existe");
        }
        PayloadDTO payloadDTO = new PayloadDTO();
        payloadDTO.setArticles(cart.get(userID));
        // al comprar debo vaciar el carrito
        ResponseDTO compraFinalizada = createTicket(payloadDTO);
        cart.remove(userID);
        return compraFinalizada;
    }

}
