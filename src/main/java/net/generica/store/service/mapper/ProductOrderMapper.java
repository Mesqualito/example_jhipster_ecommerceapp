package net.generica.store.service.mapper;

import net.generica.store.domain.*;
import net.generica.store.service.dto.ProductOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductOrder} and its DTO {@link ProductOrderDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface ProductOrderMapper extends EntityMapper<ProductOrderDTO, ProductOrder> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.email", target = "customerEmail")
    ProductOrderDTO toDto(ProductOrder productOrder);

    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "removeOrderItem", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    @Mapping(target = "removeInvoice", ignore = true)
    @Mapping(source = "customerId", target = "customer")
    ProductOrder toEntity(ProductOrderDTO productOrderDTO);

    default ProductOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductOrder productOrder = new ProductOrder();
        productOrder.setId(id);
        return productOrder;
    }
}
