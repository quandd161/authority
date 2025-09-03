package org.example.products.service;

import org.example.products.model.Payment;
import org.example.products.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public Payment createPayment(Payment payment) {
        payment.setPaymentId(null);
        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(java.time.LocalDateTime.now());
        }
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Long id, Payment payment) {
        payment.setPaymentId(id);
        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
