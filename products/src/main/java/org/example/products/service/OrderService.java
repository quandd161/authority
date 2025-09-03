package org.example.products.service;

import org.example.products.model.Customer;
import org.example.products.model.Order;
import org.example.products.model.OrderDetail;
import org.example.products.model.Product;
import org.example.products.repository.CustomerRepository;
import org.example.products.repository.OrderDetailRepository;
import org.example.products.repository.OrderRepository;
import org.example.products.repository.ProductRepository;
import org.example.products.request.OrderCreateRequest;
import org.example.products.request.OrderItemRequest;
import org.example.products.response.OrderDetailResponse;
import org.example.products.response.OrderResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public OrderResponse createOrder(OrderCreateRequest request){
        // 1. Validate customer
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // 2. Validate and prepare order details
        double totalAmount = 0;
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (OrderItemRequest itemReq : request.getOrderItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + itemReq.getProductId()));

            if (product.getQuantity() < itemReq.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
            }

            // Subtract stock
            product.setQuantity(product.getQuantity() - itemReq.getQuantity());
            productRepository.save(product);

            OrderDetail detail = new OrderDetail();
            detail.setProductId(itemReq.getProductId());
            detail.setQuantity(itemReq.getQuantity());
            detail.setUnitPrice(itemReq.getUnitPrice());
            detail.setTotal(itemReq.getQuantity()*itemReq.getUnitPrice());
            orderDetails.add(detail);

            totalAmount += itemReq.getQuantity() * itemReq.getUnitPrice();
        }

        // 3. Create and save order
        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(totalAmount);
        order.setStatus(request.getStatus());
        order.setUserId(request.getUserId());


        order = orderRepository.save(order);

        // 4. Save order details
        for (OrderDetail detail : orderDetails) {
            detail.setOrderId(order.getOrderId());
            orderDetailRepository.save(detail);
        }

        // 5. Build response
        List<OrderDetailResponse> detailResponses = orderDetails.stream()
                .map(OrderDetailResponse::fromEntity)
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getOrderId(),
                order.getOrderId(),
                order.getTotalAmount(),
                order.getOrderDate(),
                detailResponses
        );
    }

    public Order updateOrder(Long id, Order order) {
        order.setOrderId(id);
        // totalAmount sẽ được cập nhật tự động qua OrderDetailService
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // Chỉ dùng nội bộ, không public: cập nhật lại tổng tiền cho đơn
    public void updateOrderTotal(Long orderId, Double totalAmount) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setTotalAmount(totalAmount);
            orderRepository.save(order);
        });
    }
}
