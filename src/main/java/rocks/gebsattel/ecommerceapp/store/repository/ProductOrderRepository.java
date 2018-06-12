package rocks.gebsattel.ecommerceapp.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocks.gebsattel.ecommerceapp.store.domain.ProductOrder;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProductOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    Page<ProductOrder> findAllByCustomerUserLogin(String login, Pageable pageable);

}
