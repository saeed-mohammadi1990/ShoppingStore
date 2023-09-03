package market.model.service;

import market.common.CustomerException;
import market.model.entity.Cart;
import market.model.entity.Customer;
import market.model.repository.CustomerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository repository;

    private static final Logger logger = Logger.getLogger(CustomerServiceImpl.class);

    @Transactional
    @Override
    public void save(Customer customer) {
        Cart cart = new Cart();
        customer.setCart(cart);
        repository.save(customer);
    }

    @Transactional
    @Override
    public Customer update(Customer customer) {
        Optional<Customer> dbCustomer = repository.findById(customer.getId());

        if (dbCustomer.isPresent()) {
            logger.info("Customer name is: " + dbCustomer.get().getName() + " .");
            Optional.ofNullable(customer.getName()).ifPresent(item -> dbCustomer.get().setName(item));
            Optional.ofNullable(customer.getFamily()).ifPresent(item -> dbCustomer.get().setFamily(item));
            Optional.ofNullable(customer.getPhoneNumber()).ifPresent(item -> dbCustomer.get().setPhoneNumber(item));
            Optional.ofNullable(customer.getAddress()).ifPresent(item -> dbCustomer.get().setAddress(item));
            Optional.ofNullable(customer.getCart()).ifPresent(item -> dbCustomer.get().setCart(item));
            repository.save(dbCustomer.get());
            return dbCustomer.get();
        } else {
            logger.warn("Customer exception was executed.");
            throw new CustomerException("Not Found Customer For Update.");
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Optional<Customer> dbCustomer = repository.findById(id);
        if (dbCustomer.isPresent()) {
            logger.info("Customer " + dbCustomer.get().getName() + " was deleted.");
            if (dbCustomer.get().getCart().getProductionList().size() > 0) {
                dbCustomer.get().getCart().getProductionList().clear();
            }
            repository.delete(dbCustomer.get());
        } else {
            logger.warn("Customer exception was executed.");
            throw new CustomerException("Not Found Customer For Delete.");
        }
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long customerId) {
        return repository.findById(customerId);
    }
}
