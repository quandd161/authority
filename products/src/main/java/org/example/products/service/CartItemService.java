package org.example.products.service;

import org.example.products.model.CartItem;
import org.example.products.repository.CartItemRepository;
import org.example.products.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;

    public CartItemService(CartItemRepository cartItemRepository, CartRepository cartRepository, CartService cartService) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
    }

    public List<CartItem> getCartItemsByCartId(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

    public Optional<CartItem> getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId);
    }

    public CartItem createCartItem(CartItem item) {
        item.setCartItemId(null);
        item.setTotal(item.getUnitPrice() * item.getQuantity());
        CartItem saved = cartItemRepository.save(item);
        updateCartTotal(item.getCartId());
        return saved;
    }

    public CartItem updateCartItem(Long cartItemId, CartItem item) {
        item.setCartItemId(cartItemId);
        item.setTotal(item.getUnitPrice() * item.getQuantity());
        CartItem saved = cartItemRepository.save(item);
        updateCartTotal(item.getCartId());
        return saved;
    }

    public void deleteCartItem(Long cartItemId) {
        Optional<CartItem> itemOpt = cartItemRepository.findById(cartItemId);
        itemOpt.ifPresent(item -> {
            cartItemRepository.deleteById(cartItemId);
            updateCartTotal(item.getCartId());
        });
    }

    private void updateCartTotal(Long cartId) {
        List<CartItem> items = cartItemRepository.findByCartId(cartId);
        double total = items.stream().mapToDouble(CartItem::getTotal).sum();
        cartService.updateCartTotal(cartId, total);
    }
}
