package rocks.gebsattel.ecommerceapp.store.repository;

import rocks.gebsattel.ecommerceapp.store.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
