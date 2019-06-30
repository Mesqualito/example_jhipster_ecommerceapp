package net.generica.store.service.mapper;

import net.generica.store.domain.*;
import net.generica.store.service.dto.ProductReferenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductReference} and its DTO {@link ProductReferenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ProductReferenceMapper extends EntityMapper<ProductReferenceDTO, ProductReference> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    ProductReferenceDTO toDto(ProductReference productReference);

    @Mapping(source = "productId", target = "product")
    ProductReference toEntity(ProductReferenceDTO productReferenceDTO);

    default ProductReference fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductReference productReference = new ProductReference();
        productReference.setId(id);
        return productReference;
    }
}
