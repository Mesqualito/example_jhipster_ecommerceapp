package net.generica.store.service.mapper;

import net.generica.store.domain.*;
import net.generica.store.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductSubstitutionMapper.class, ProductCategoryMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.name", target = "productCategoryName")
    ProductDTO toDto(Product product);

    @Mapping(target = "shopImages", ignore = true)
    @Mapping(target = "removeShopImage", ignore = true)
    @Mapping(target = "references", ignore = true)
    @Mapping(target = "removeReference", ignore = true)
    @Mapping(target = "removeSubstitution", ignore = true)
    @Mapping(source = "productCategoryId", target = "productCategory")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
