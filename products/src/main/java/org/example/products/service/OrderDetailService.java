package org.example.products.service;

import org.example.products.model.OrderDetail;
import org.example.products.model.Product;
import org.example.products.repository.OrderDetailRepository;
import org.example.products.repository.OrderRepository;
import org.example.products.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderService orderService;

    public OrderDetailService(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository, ProductRepository productRepository, OrderService orderService) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderService = orderService;
    }

    // Lấy tất cả chi tiết đơn hàng của 1 đơn
    public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    public Optional<OrderDetail> getOrderDetailById(Long id) {
        return orderDetailRepository.findById(id);
    }

    public OrderDetail createOrderDetail(OrderDetail detail) {
        detail.setOrderDetailId(null);
        detail.setTotal(detail.getUnitPrice() * detail.getQuantity());

        // Giảm tồn kho sản phẩm
        Product product = productRepository.findById(detail.getProductId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));
        if (product.getQuantity() < detail.getQuantity()) {
            throw new RuntimeException("Số lượng tồn kho không đủ!");
        }
        product.setQuantity(product.getQuantity() - detail.getQuantity());
        productRepository.save(product);

        OrderDetail saved = orderDetailRepository.save(detail);
        updateOrderTotal(detail.getOrderId());
        return saved;
    }

    public OrderDetail updateOrderDetail(Long id, OrderDetail newDetail) {
        OrderDetail oldDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy order detail!"));

        // Tính chênh lệch số lượng để cập nhật tồn kho
        int deltaQty = newDetail.getQuantity() - oldDetail.getQuantity();
        Product product = productRepository.findById(newDetail.getProductId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));
        int newStock = product.getQuantity() - deltaQty;
        if (newStock < 0) throw new RuntimeException("Số lượng tồn kho không đủ!");
        product.setQuantity(newStock);
        productRepository.save(product);

        newDetail.setOrderDetailId(id);
        newDetail.setTotal(newDetail.getUnitPrice() * newDetail.getQuantity());
        OrderDetail saved = orderDetailRepository.save(newDetail);
        updateOrderTotal(newDetail.getOrderId());
        return saved;
    }

    public void deleteOrderDetail(Long id) {
        OrderDetail detail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy order detail!"));
        // Hoàn lại tồn kho
        Product product = productRepository.findById(detail.getProductId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));
        product.setQuantity(product.getQuantity() + detail.getQuantity());
        productRepository.save(product);

        orderDetailRepository.deleteById(id);
        updateOrderTotal(detail.getOrderId());
    }

    // Hàm cập nhật lại tổng tiền đơn hàng
    private void updateOrderTotal(Long orderId) {
        List<OrderDetail> details = orderDetailRepository.findByOrderId(orderId);
        double total = details.stream()
                .mapToDouble(OrderDetail::getTotal)
                .sum();
        orderService.updateOrderTotal(orderId, total);
    }
}
