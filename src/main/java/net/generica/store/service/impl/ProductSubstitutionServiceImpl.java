package net.generica.store.service.impl;

import net.generica.store.service.ProductSubstitutionService;
import net.generica.store.domain.ProductSubstitution;
import net.generica.store.repository.ProductSubstitutionRepository;
import net.generica.store.service.dto.ProductSubstitutionDTO;
import net.generica.store.service.mapper.ProductSubstitutionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductSubstitution}.
 */
@Service
@Transactional
public class ProductSubstitutionServiceImpl implements ProductSubstitutionService {

    private final Logger log = LoggerFactory.getLogger(ProductSubstitutionServiceImpl.class);

    private final ProductSubstitutionRepository productSubstitutionRepository;

    private final ProductSubstitutionMapper productSubstitutionMapper;

    public ProductSubstitutionServiceImpl(ProductSubstitutionRepository productSubstitutionRepository, ProductSubstitutionMapper productSubstitutionMapper) {
        this.productSubstitutionRepository = productSubstitutionRepository;
        this.productSubstitutionMapper = productSubstitutionMapper;
    }

    /**
     * Save a productSubstitution.
     *
     * @param productSubstitutionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductSubstitutionDTO save(ProductSubstitutionDTO productSubstitutionDTO) {
        log.debug("Request to save ProductSubstitution : {}", productSubstitutionDTO);
        ProductSubstitution productSubstitution = productSubstitutionMapper.toEntity(productSubstitutionDTO);
        productSubstitution = productSubstitutionRepository.save(productSubstitution);
        return productSubstitutionMapper.toDto(productSubstitution);
    }

    /**
     * Get all the productSubstitutions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductSubstitutionDTO> findAll() {
        log.debug("Request to get all ProductSubstitutions");
        return productSubstitutionRepository.findAll().stream()
            .map(productSubstitutionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productSubstitution by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductSubstitutionDTO> findOne(Long id) {
        log.debug("Request to get ProductSubstitution : {}", id);
        return productSubstitutionRepository.findById(id)
            .map(productSubstitutionMapper::toDto);
    }

    /**
     * Delete the productSubstitution by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductSubstitution : {}", id);
        productSubstitutionRepository.deleteById(id);
    }
}
