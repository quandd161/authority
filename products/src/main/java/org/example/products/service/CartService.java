package org.example.products.service;

import org.example.products.model.Cart;
import org.example.products.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Optional<Cart> getActiveCartByCustomerId(Long customerId) {
        return cartRepository.findByCustomerIdAndCheckedOutFalse(customerId);
    }

    public Cart createCart(Long customerId) {
        Cart cart = new Cart();
        cart.setCustomerId(customerId);
        cart.setTotalAmount(0.0);
        cart.setCheckedOut(false);
        return cartRepository.save(cart);
    }

    public void updateCartTotal(Long cartId, Double totalAmount) {
        cartRepository.findById(cartId).ifPresent(cart -> {
            cart.setTotalAmount(totalAmount);
            cartRepository.save(cart);
        });
    }

    public Optional<Cart> getCartById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    public void checkoutCart(Long cartId) {
        cartRepository.findById(cartId).ifPresent(cart -> {
            cart.setCheckedOut(true);
            cartRepository.save(cart);
        });
    }
}
