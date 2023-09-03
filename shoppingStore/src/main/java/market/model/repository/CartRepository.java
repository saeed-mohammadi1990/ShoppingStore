package market.model.repository;

import market.model.entity.Cart;
import market.model.entity.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findCartByProductionList(Production production);
}
