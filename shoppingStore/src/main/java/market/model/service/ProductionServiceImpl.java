package market.model.service;

import market.common.ProductionException;
import market.model.entity.Cart;
import market.model.entity.Production;
import market.model.repository.ProductionRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductionServiceImpl implements ProductionService {

    @Autowired
    private ProductionRepository repository;
    @Autowired
    private CartService cartService;

    private static final Logger logger = Logger.getLogger(ProductionServiceImpl.class);

    @Transactional
    @Override
    public void save(Production production) {
        repository.save(production);
    }

    @Transactional
    @Override
    public Production update(Production production) {
        Optional<Production> dbProduction = repository.findById(production.getId());
        if (dbProduction.isPresent()) {
            logger.info("Production name is: " + dbProduction.get().getName() + " .");
            Optional.ofNullable(production.getName()).ifPresent(item -> dbProduction.get().setName(item));
            Optional.ofNullable(production.getPrice()).ifPresent(item -> dbProduction.get().setPrice(item));
            Optional.ofNullable(production.getDescription()).ifPresent(item -> dbProduction.get().setDescription(item));
            Optional.ofNullable(production.getCartList()).ifPresent(items -> dbProduction.get().setCartList(items));
            repository.save(dbProduction.get());

            List<Cart> cartByProduction = cartService.findCartByProduction(dbProduction.get());
            for (Cart cart : cartByProduction) {
                cartService.calculatePrice(cart);
            }
            return dbProduction.get();
        } else {
            logger.warn("Production exception was executed.");
            throw new ProductionException("Not Found Production For Update");
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Optional<Production> dbProduction = repository.findById(id);
        if (dbProduction.isPresent()) {
            logger.info("Production name is: " + dbProduction.get().getName() + " .");

            List<Cart> cartByProduction = cartService.findCartByProduction(dbProduction.get());
            if (cartByProduction.size() > 0) {
                throw new ProductionException("You can't remove production " + dbProduction.get().getName() + " because it has relation.");
            } else {
                repository.delete(dbProduction.get());
            }
        } else {
            logger.warn("Production exception was executed.");
            throw new ProductionException("Not Found Production For Delete");
        }
    }

    @Override
    public List<Production> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Production> findById(Long productionId) {
        return repository.findById(productionId);
    }
}
