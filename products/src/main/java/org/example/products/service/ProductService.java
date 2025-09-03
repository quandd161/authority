package org.example.products.service;

import org.example.products.model.Product;
import org.example.products.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> getProductByCode(String code) {
        return productRepository.findByCode(code);
    }

    public Product createProduct(Product product) {
        product.setProductId(null);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        product.setProductId(id);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Cập nhật tồn kho khi tạo/sửa/xóa OrderDetail
    public void changeStock(Long productId, int delta) {
        productRepository.findById(productId).ifPresent(product -> {
            int newQty = product.getQuantity() + delta;
            if (newQty < 0) throw new RuntimeException("Số lượng tồn kho không đủ!");
            product.setQuantity(newQty);
            productRepository.save(product);
        });
    }
}
