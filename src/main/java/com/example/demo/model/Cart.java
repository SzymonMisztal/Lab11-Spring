package com.example.demo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    List<CartProduct> products;

    public String toJSON() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.getProducts().forEach(cartProduct -> cartProduct.getProduct().getCategory().setProducts(null));
        return URLEncoder.encode(objectMapper.writeValueAsString(this), StandardCharsets.UTF_8);
    }

    public static Cart fromJSON(String json) throws JsonProcessingException {
        if (json.equals("")) {
            return new Cart(Collections.emptyList());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(URLDecoder.decode(json, StandardCharsets.UTF_8), Cart.class);
    }
}
