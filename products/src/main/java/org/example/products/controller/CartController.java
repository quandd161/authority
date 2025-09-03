package org.example.products.controller;

import org.example.products.model.Cart;
import org.example.products.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Cart> getActiveCartByCustomerId(@PathVariable Long customerId) {
        Optional<Cart> cart = cartService.getActiveCartByCustomerId(customerId);
        return cart.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/customer/{customerId}")
    public Cart createCart(@PathVariable Long customerId) {
        return cartService.createCart(customerId);
    }

    @PostMapping("/{cartId}/checkout")
    public ResponseEntity<?> checkoutCart(@PathVariable Long cartId) {
        cartService.checkoutCart(cartId);
        return ResponseEntity.ok().build();
    }
}
