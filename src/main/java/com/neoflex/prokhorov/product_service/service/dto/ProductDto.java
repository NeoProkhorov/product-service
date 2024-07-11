package com.neoflex.prokhorov.product_service.service.dto;

import com.neoflex.prokhorov.product_service.value.ProductType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
public class ProductDto {
    UUID id;
    @NotNull
    String name;
    @NotNull
    ProductType type;
    @NotNull
    LocalDate startDate;
    @NotNull
    LocalDate endDate;
    String description;
    @NotNull
    UUID tariffId;
    int version;
    Instant lastModifiedInstant;
    Long accountId;
}
