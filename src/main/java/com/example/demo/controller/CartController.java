package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.service.CartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;

@Controller
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/user/cart")
    public String getCartView(Model model, HttpServletRequest request) throws JsonProcessingException {
        String cookieStr = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("cart_cookie"))
                .map(Cookie::getValue)
                .findAny().orElse("");

        Cart cart = Cart.fromJSON(cookieStr);

        model.addAttribute("cart", cart);

        double price = cartService.getOverallPrice(cart);
        model.addAttribute("price", price);

        return "cart";
    }

    @GetMapping("/user/cart/add/{id}")
    public String addProduct(@PathVariable long id,
                             HttpServletRequest request,
                             HttpServletResponse response) throws JsonProcessingException {
        String cookieStr = extractCookie(request);
        Cart cart = Cart.fromJSON(cookieStr);
        Cart newCart = cartService.addProduct(id, cart);
        Cookie cookie = new Cookie("cart_cookie", newCart.toJSON());
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/user/cart";
    }

    @GetMapping("/user/cart/remove/{id}")
    public String removeProduct(@PathVariable long id,
                                HttpServletRequest request,
                                HttpServletResponse response) throws JsonProcessingException {
        String cookieStr = extractCookie(request);
        Cart cart = Cart.fromJSON(cookieStr);
        Cart newCart = cartService.removeProduct(id, cart);
        Cookie cookie = new Cookie("cart_cookie", newCart.toJSON());
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/user/cart";
    }

    @GetMapping("/user/cart/decrease/{id}")
    public String decreaseProduct(@PathVariable long id,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws JsonProcessingException {
        String cookieStr = extractCookie(request);
        Cart cart = Cart.fromJSON(cookieStr);
        Cart newCart = cartService.decreaseProduct(id, cart);
        Cookie cookie = new Cookie("cart_cookie", newCart.toJSON());
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/user/cart";
    }

    private String extractCookie(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("cart_cookie"))
                .map(Cookie::getValue)
                .findAny().orElse("");
    }
}
