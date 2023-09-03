package market.controller;

import market.model.service.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartServiceImpl service;

    @PutMapping("/{cartId}/add/{productionId}")
    public Object addToCart(@PathVariable Long cartId, @PathVariable Long productionId) {
        return ResponseEntity.ok(service.addToCart(cartId, productionId));
    }

    @DeleteMapping("/{cartId}/delete/{productionId}")
    public Object deleteFromCart(@PathVariable Long cartId, @PathVariable Long productionId) {
        return ResponseEntity.ok(service.deleteFromCart(cartId, productionId));
    }
}
