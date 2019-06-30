package net.generica.store.repository;

import net.generica.store.domain.ShopImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ShopImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShopImageRepository extends JpaRepository<ShopImage, Long>, JpaSpecificationExecutor<ShopImage> {

}
