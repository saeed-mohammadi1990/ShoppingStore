package market.controller;

import market.model.entity.Customer;
import market.model.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerServiceImpl service;

    @PostMapping("/save")
    public Object save(@RequestBody Customer customer) {
        service.save(customer);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/update")
    public Object update(@RequestBody Customer customer) {
        Customer newCustomer = service.update(customer);
        return ResponseEntity.ok(newCustomer);
    }

    @DeleteMapping("/delete/{id}")
    public Object delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Successfully");
    }

    @GetMapping("/findAll")
    public Object findAll() {
        return ResponseEntity.ok(service.findAll());
    }
}
