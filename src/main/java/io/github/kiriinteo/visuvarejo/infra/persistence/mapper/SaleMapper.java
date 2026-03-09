package io.github.kiriinteo.visuvarejo.infra.persistence.mapper;

import io.github.kiriinteo.visuvarejo.core.domain.*;
import io.github.kiriinteo.visuvarejo.infra.persistence.entity.*;

public class SaleMapper {

    public static SaleEntity toEntity(Sale sale) {

        SaleEntity saleEntity = new SaleEntity(
                sale.getId(),
                sale.getDate(),
                sale.getCompanyId()
        );

        sale.getItems().forEach(item -> {

            SaleItemEntity itemEntity = new SaleItemEntity(
                saleEntity,
                item.getProductId(),
                item.getQuantity(),
                item.getUnitPrice().getValue(),
                item.getName(),
                item.getCompanyId()
            );

            saleEntity.getItems().add(itemEntity);
        });

        return saleEntity;
    }

    public static Sale toDomain(SaleEntity entity) {

        Sale sale = new Sale(entity.getId(), entity.getCompanyId());

        entity.getItems().forEach(itemEntity -> {

            SaleItem item = new SaleItem(
                itemEntity.getProductId(),
                itemEntity.getQuantity(),
                new Money(itemEntity.getUnitPrice()),
                itemEntity.getName(),
                itemEntity.getCompanyId()
            );

            sale.addItem(item);
        });

        return sale;
    }
}

