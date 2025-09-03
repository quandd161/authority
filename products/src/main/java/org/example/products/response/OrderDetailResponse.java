package org.example.products.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.products.model.OrderDetail;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
    private Long productId;
    private Integer quantity;
    private Double unitPrice;

    public static OrderDetailResponse fromEntity(OrderDetail detail) {
        return new OrderDetailResponse(
                detail.getProductId(),
                detail.getQuantity(),
                detail.getUnitPrice()
        );
    }

}
