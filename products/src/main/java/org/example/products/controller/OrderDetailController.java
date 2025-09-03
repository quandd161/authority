package org.example.products.controller;

import org.example.products.model.OrderDetail;
import org.example.products.service.OrderDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/order/{orderId}")
    public List<OrderDetail> getOrderDetailsByOrderId(@PathVariable Long orderId) {
        return orderDetailService.getOrderDetailsByOrderId(orderId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable Long id) {
        Optional<OrderDetail> detail = orderDetailService.getOrderDetailById(id);
        return detail.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public OrderDetail createOrderDetail(@RequestBody OrderDetail detail) {
        return orderDetailService.createOrderDetail(detail);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetail> updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetail detail) {
        if (orderDetailService.getOrderDetailById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderDetailService.updateOrderDetail(id, detail));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable Long id) {
        if (orderDetailService.getOrderDetailById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.noContent().build();
    }
}