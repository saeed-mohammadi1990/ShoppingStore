package market.model.service;

import market.common.CustomerException;
import market.common.ProductionException;
import market.model.entity.Cart;
import market.model.entity.Customer;
import market.model.entity.Production;
import market.model.repository.CartRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductionService productionService;
    @Autowired
    private CartRepository repository;

    private static final Logger logger = Logger.getLogger(CartServiceImpl.class);

    @Override
    public Customer addToCart(Long customerId, Long productionId) {
        Cart cart;
        Optional<Customer> dbCustomer = customerService.findById(customerId);
        Optional<Production> dbProduction = productionService.findById(productionId);

        if (dbCustomer.isPresent()) {
            logger.info("Customer name is: " + dbCustomer.get().getName() + " .");
            cart = dbCustomer.get().getCart();
        } else {
            logger.warn("Customer exception was executed.");
            throw new CustomerException("Not Found Customer.");
        }
        if (dbProduction.isPresent()) {
            logger.info("Production name is: " + dbProduction.get().getName() + " .");
            List<Production> productionList = cart.getProductionList();
            int count = 0;

            for (Production production : productionList) {
                if (production.getId().equals(dbProduction.get().getId())) {
                    count++;
                }
            }
            if (count > 0) {
                throw new ProductionException("Product " + dbProduction.get().getName() + " is in your cart.");
            } else {
                cart.getProductionList().add(dbProduction.get());
            }
        } else {
            logger.warn("Production exception was executed.");
            throw new ProductionException("Not Found Production.");
        }

        calculatePrice(cart);
        return customerService.update(dbCustomer.get());
    }

    @Override
    public Customer deleteFromCart(Long customerId, Long productionId) {
        Optional<Customer> dbCustomer = customerService.findById(customerId);
        Optional<Production> dbProduction = productionService.findById(productionId);

        Cart cart;
        if (dbCustomer.isPresent()) {
            logger.info("Customer name is: " + dbCustomer.get().getName() + " .");
            cart = dbCustomer.get().getCart();
        } else {
            logger.warn("Customer exception was executed.");
            throw new CustomerException("Not Found Customer.");
        }
        if (dbProduction.isPresent()) {
            List<Production> productionList = dbCustomer.get().getCart().getProductionList();
            int count = 0;

            for (Production production : productionList) {
                if (production.getId().equals(dbProduction.get().getId())) {
                    count++;
                    cart.getProductionList().remove(dbProduction.get());
                    logger.info("Production " + dbProduction.get().getName() + " was deleted.");
                    break;
                }
            }
            if (count == 0) {
                throw new ProductionException("Product " + dbProduction.get().getName() + " isn't in your cart.");
            }
        } else {
            logger.warn("Production exception was executed.");
            throw new ProductionException("Not Found Production.");
        }
        calculatePrice(cart);
        return customerService.update(dbCustomer.get());
    }

    @Override
    public void calculatePrice(Cart cart) {
        List<Production> productionList = cart.getProductionList();
        Long price = 0L;
        for (Production production : productionList) {
            price += production.getPrice();
        }
        cart.setTotalPrice(price);
    }

    @Override
    public List<Cart> findCartByProduction(Production production) {
        return repository.findCartByProductionList(production);
    }

}
