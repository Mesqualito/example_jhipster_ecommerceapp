package net.generica.store.service.mapper;

import net.generica.store.domain.*;
import net.generica.store.service.dto.ProductSubstitutionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductSubstitution} and its DTO {@link ProductSubstitutionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductSubstitutionMapper extends EntityMapper<ProductSubstitutionDTO, ProductSubstitution> {


    @Mapping(target = "products", ignore = true)
    @Mapping(target = "removeProduct", ignore = true)
    ProductSubstitution toEntity(ProductSubstitutionDTO productSubstitutionDTO);

    default ProductSubstitution fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductSubstitution productSubstitution = new ProductSubstitution();
        productSubstitution.setId(id);
        return productSubstitution;
    }
}
