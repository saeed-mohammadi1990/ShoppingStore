package market.model.service;

import market.model.entity.Cart;
import market.model.entity.Customer;
import market.model.entity.Production;

import java.util.List;

public interface CartService {

    Customer addToCart(Long idC, Long idP);

    Customer deleteFromCart(Long idc, Long idP);

    void calculatePrice(Cart cart);

    List<Cart> findCartByProduction(Production production);
}
