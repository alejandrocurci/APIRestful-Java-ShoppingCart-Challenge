Spring Boot API - Simple shopping platform

INSTRUCTIONS

There are two different and independent controllers: "Products" and "Clients"

PRODUCT SERVICE:

1- Get a list with every available product (GET: api/v1/articles)

2- The previous endpoint allows the use of optional filters:

  a- Filtering with "category", "brand", "price", "freeShipping", "prestige" (price filter refers to the maximum price limit)
  It is limited to two filters simultaneously.
  
  b- Order products by name (0 y 1) and by price (2 y 3) with param "order"
  
3- Make a purchase to (POST: api/v1/purchase-request)

  a- Requires a body object like the following example:
  
      {
        "articles": [
            {
                "productId": 1,
                "name": "Desmalezadora",
                "brand": "Makita",
                "quantity": 3
            },
            {
                "productId": 5,
                "name": "Zapatillas Deportivas",
                "brand": "Nike",
                "quantity": 2
            },
            {
                "productId": 9,
                "name": "Remera",
                "brand": "Taverniti",
                "quantity": 2
            }
        ]
    }
    
4- Add several articles to the shopping cart (POST: api/v1/cart)

  a- Requires a param "userID", which will identify the user and the cart.
  
  b- The first request with a new "userID" will prepare the user-cart identification for further requests.

  c- Requires the same body object as the previous section.
  
  d- To review the articles added to the cart, use this same request with an empty body
  
5- Purchase the articles added to the shopping cart (POST: api/v1/cart/purchase)

  a- Requires the param "userID" which was first generated in section 4
  
This service does not persist information in a database. All data is handled in memory.

CLIENT SERVICE:

1- Register a client (POST: api/v2/client-create)

  a- Requires a "password" param in the header. Numbers, lower and upper case letters are allowed (between 6 and 12 characters, such as: "Ab1234")
  
  b- Requires a body object with the client information, such as:
  
      {
        "id": 56920,
        "name": "John",
        "email": "john@yahoo.com.ar",
        "city": "New York"
      }
      
2- Get the client list (GET: api/v2/client-list)

  a- It allows the use of an optional param "city", which acts as a search filter.

This service persist the information in a CSV file.


  
