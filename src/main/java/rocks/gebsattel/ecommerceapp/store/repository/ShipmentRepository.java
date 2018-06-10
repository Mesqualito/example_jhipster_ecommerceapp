package rocks.gebsattel.ecommerceapp.store.repository;

import rocks.gebsattel.ecommerceapp.store.domain.Shipment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Shipment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

}
