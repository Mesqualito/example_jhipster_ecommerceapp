package net.generica.store.service.mapper;

import net.generica.store.domain.*;
import net.generica.store.service.dto.InvoiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Invoice} and its DTO {@link InvoiceDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductOrderMapper.class})
public interface InvoiceMapper extends EntityMapper<InvoiceDTO, Invoice> {

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "order.code", target = "orderCode")
    InvoiceDTO toDto(Invoice invoice);

    @Mapping(target = "shipments", ignore = true)
    @Mapping(target = "removeShipment", ignore = true)
    @Mapping(source = "orderId", target = "order")
    Invoice toEntity(InvoiceDTO invoiceDTO);

    default Invoice fromId(Long id) {
        if (id == null) {
            return null;
        }
        Invoice invoice = new Invoice();
        invoice.setId(id);
        return invoice;
    }
}
