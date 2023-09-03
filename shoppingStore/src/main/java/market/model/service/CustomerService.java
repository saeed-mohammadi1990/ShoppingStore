package market.model.service;

import market.model.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    void save(Customer customer);

    Customer update(Customer customer);

    void delete(Long id);

    List<Customer> findAll();

    Optional<Customer> findById(Long customerId);
}
