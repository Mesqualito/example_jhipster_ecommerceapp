package net.generica.store.service.mapper;

import net.generica.store.domain.*;
import net.generica.store.service.dto.ShipmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Shipment and its DTO ShipmentDTO.
 */
@Mapper(componentModel = "spring", uses = {InvoiceMapper.class})
public interface ShipmentMapper extends EntityMapper<ShipmentDTO, Shipment> {

    @Mapping(source = "invoice.id", target = "invoiceId")
    @Mapping(source = "invoice.code", target = "invoiceCode")
    ShipmentDTO toDto(Shipment shipment);

    @Mapping(source = "invoiceId", target = "invoice")
    Shipment toEntity(ShipmentDTO shipmentDTO);

    default Shipment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Shipment shipment = new Shipment();
        shipment.setId(id);
        return shipment;
    }
}
