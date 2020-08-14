package ru.simbirsoft.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.warehouse_management.model.Shop;

import java.util.Optional;

/** Jpa repository for Shop entitty */
public interface ShopRepository extends JpaRepository<Shop, Long> {
  /**
   * Returns optional of shop with such name
   *
   * @param name - name of the shop
   * @return - Optional of Shop
   */
  Optional<Shop> findByName(String name);
}
