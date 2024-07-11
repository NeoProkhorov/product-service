package com.neoflex.prokhorov.product_service.domain;

import com.neoflex.prokhorov.product_service.value.ProductType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Audited
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends AbstractPersistable<UUID> {
    @NotNull
    String name;
    @NotNull
    @Enumerated(EnumType.STRING)
    ProductType type;
    @NotNull
    LocalDate startDate;
    @NotNull
    LocalDate endDate;
    String description;
    @NotNull
    UUID tariffId;
    int version;
    @LastModifiedDate
    Instant lastModifiedInstant;
    @LastModifiedBy
    Long accountId;


    public int updateVersion() {
        return ++ this.version;
    }
}
