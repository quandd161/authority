package org.example.products.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
    private Long customerId;
    private List<OrderItemRequest> orderItems;
    private String status;
    private Long userId;


}

