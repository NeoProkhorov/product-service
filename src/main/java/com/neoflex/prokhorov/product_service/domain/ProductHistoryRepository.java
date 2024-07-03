package com.neoflex.prokhorov.product_service.domain;

import jakarta.persistence.NoResultException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ProductHistoryRepository {
    List<Product> getAllVersions(UUID id);

    List<Product> getPreviousVersion(UUID id, int version) throws NoResultException;

    List<Product> getVersionsByPeriod(UUID id, Instant timeFrom, Instant timeTo);
}
