package net.generica.store.service.impl;

import net.generica.store.service.ProductOrderService;
import net.generica.store.domain.ProductOrder;
import net.generica.store.repository.ProductOrderRepository;
import net.generica.store.service.dto.ProductOrderDTO;
import net.generica.store.service.mapper.ProductOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductOrder}.
 */
@Service
@Transactional
public class ProductOrderServiceImpl implements ProductOrderService {

    private final Logger log = LoggerFactory.getLogger(ProductOrderServiceImpl.class);

    private final ProductOrderRepository productOrderRepository;

    private final ProductOrderMapper productOrderMapper;

    public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository, ProductOrderMapper productOrderMapper) {
        this.productOrderRepository = productOrderRepository;
        this.productOrderMapper = productOrderMapper;
    }

    /**
     * Save a productOrder.
     *
     * @param productOrderDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductOrderDTO save(ProductOrderDTO productOrderDTO) {
        log.debug("Request to save ProductOrder : {}", productOrderDTO);
        ProductOrder productOrder = productOrderMapper.toEntity(productOrderDTO);
        productOrder = productOrderRepository.save(productOrder);
        return productOrderMapper.toDto(productOrder);
    }

    /**
     * Get all the productOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductOrders");
        return productOrderRepository.findAll(pageable)
            .map(productOrderMapper::toDto);
    }


    /**
     * Get one productOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductOrderDTO> findOne(Long id) {
        log.debug("Request to get ProductOrder : {}", id);
        return productOrderRepository.findById(id)
            .map(productOrderMapper::toDto);
    }

    /**
     * Delete the productOrder by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductOrder : {}", id);
        productOrderRepository.deleteById(id);
    }
}
