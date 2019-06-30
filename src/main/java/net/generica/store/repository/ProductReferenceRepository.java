package net.generica.store.repository;

import net.generica.store.domain.ProductReference;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductReference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductReferenceRepository extends JpaRepository<ProductReference, Long>, JpaSpecificationExecutor<ProductReference> {

}
