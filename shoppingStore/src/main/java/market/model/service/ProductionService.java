package market.model.service;

import market.model.entity.Production;

import java.util.List;
import java.util.Optional;

public interface ProductionService {

    void save(Production production);

    Production update(Production production);

    void delete(Long id);

    List<Production> findAll();

    Optional<Production> findById(Long productionId);
}
