package net.generica.store.repository;

import net.generica.store.domain.ProductSubstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductSubstitution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSubstitutionRepository extends JpaRepository<ProductSubstitution, Long>, JpaSpecificationExecutor<ProductSubstitution> {

}
