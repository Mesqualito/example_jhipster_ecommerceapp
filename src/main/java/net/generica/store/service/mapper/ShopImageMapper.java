package net.generica.store.service.mapper;

import net.generica.store.domain.*;
import net.generica.store.service.dto.ShopImageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShopImage} and its DTO {@link ShopImageDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ShopImageMapper extends EntityMapper<ShopImageDTO, ShopImage> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    ShopImageDTO toDto(ShopImage shopImage);

    @Mapping(source = "productId", target = "product")
    ShopImage toEntity(ShopImageDTO shopImageDTO);

    default ShopImage fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShopImage shopImage = new ShopImage();
        shopImage.setId(id);
        return shopImage;
    }
}
