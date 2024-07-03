package com.neoflex.prokhorov.product_service.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductHistoryRepositoryImpl implements ProductHistoryRepository{
    @Autowired
    AuditReader auditReader;

    static final String ID = "id";
    static final String LAST_MODIFIED_INSTANT = "lastModifiedInstant";
    static final String VERSION = "version";

    @Override
    public List<Product> getAllVersions(UUID id) {
        return getBaseQuery(id).getResultList();
    }

    @Override
    public List<Product> getPreviousVersion(UUID id, int version) {
        return getBaseQuery(id).add(AuditEntity.property(VERSION).eq(version)).getResultList();
    }

    @Override
    public List<Product> getVersionsByPeriod(UUID id, Instant timeFrom, Instant timeTo) {
        return getBaseQuery(id).add(AuditEntity.property(LAST_MODIFIED_INSTANT)
                .between(timeFrom, timeTo)
            ).getResultList();
    }

    private AuditQuery getBaseQuery(UUID id) {
        return auditReader.createQuery()
            .forRevisionsOfEntity(Product.class, true, false)
            .add(AuditEntity.property(ID).eq(id));
    }
}
