package rocks.gebsattel.ecommerceapp.store.repository;

import rocks.gebsattel.ecommerceapp.store.domain.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
