package org.example.products.model;

import jakarta.persistence.*;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Double totalAmount = 0.0;

    @Column(nullable = false)
    private Boolean checkedOut = false; // true nếu đã chuyển thành Order

    // Getters and Setters
    public Long getCartId() { return cartId; }
    public void setCartId(Long cartId) { this.cartId = cartId; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public Boolean getCheckedOut() { return checkedOut; }
    public void setCheckedOut(Boolean checkedOut) { this.checkedOut = checkedOut; }
}
