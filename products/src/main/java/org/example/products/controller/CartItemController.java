package org.example.products.controller;

import org.example.products.model.CartItem;
import org.example.products.service.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("/cart/{cartId}")
    public List<CartItem> getCartItemsByCartId(@PathVariable Long cartId) {
        return cartItemService.getCartItemsByCartId(cartId);
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long cartItemId) {
        Optional<CartItem> item = cartItemService.getCartItemById(cartItemId);
        return item.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CartItem createCartItem(@RequestBody CartItem item) {
        return cartItemService.createCartItem(item);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long cartItemId, @RequestBody CartItem item) {
        if (cartItemService.getCartItemById(cartItemId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartItemService.updateCartItem(cartItemId, item));
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long cartItemId) {
        if (cartItemService.getCartItemById(cartItemId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        cartItemService.deleteCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }
}
