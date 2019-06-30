package net.generica.store.service.mapper;

import net.generica.store.domain.*;
import net.generica.store.service.dto.ProductCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductCategory} and its DTO {@link ProductCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductCategoryMapper extends EntityMapper<ProductCategoryDTO, ProductCategory> {


    @Mapping(target = "products", ignore = true)
    @Mapping(target = "removeProduct", ignore = true)
    ProductCategory toEntity(ProductCategoryDTO productCategoryDTO);

    default ProductCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(id);
        return productCategory;
    }
}
