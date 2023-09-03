package market.controller;

import market.model.entity.Production;
import market.model.service.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/production")
public class ProductionController {
    @Autowired
    private ProductionService service;

    @PostMapping("/save")
    public Object save(@RequestBody Production production) {
        service.save(production);
        return ResponseEntity.ok(production);
    }

    @PutMapping("/update")
    public Object update(@RequestBody Production production) {
        Production newProduction = service.update(production);
        return ResponseEntity.ok(newProduction);
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
