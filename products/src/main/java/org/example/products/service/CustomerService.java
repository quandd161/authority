package org.example.products.service;

import org.example.products.model.Customer;
import org.example.products.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer createCustomer(Customer customer) {
        customer.setCustomerId(null);
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        customer.setCustomerId(id);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
