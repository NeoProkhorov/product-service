package com.neoflex.prokhorov.product_service.service;

import com.neoflex.prokhorov.product_service.domain.Product;
import com.neoflex.prokhorov.product_service.domain.ProductRepository;
import com.neoflex.prokhorov.product_service.service.dto.ProductDto;
import com.neoflex.prokhorov.product_service.service.mapper.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductService {
    ProductRepository repository;
    ProductMapper mapper;

    static final String ENTITY_NOT_FOUND_EXCEPTION = "Не найден продукт с id %s";
    static final String VERSIONS_FIND_EXCEPTION = "Найдено несколько версий продукта с id %s за данный период";
    static final String VERSION_NOT_FOUND_PERIOD_EXCEPTION = "Не найдена версия продукта с id %s за данный период";
    static final String VERSION_NOT_FOUND_EXCEPTION = "Не найдена версия %s продукта с id %s";

    public List<ProductDto> getAll() {
        return mapper.toListDto(repository.findAll());
    }

    public ProductDto getById(UUID id) {
        Product product = findById(id);
        return mapper.toDto(product);
    }

    public ProductDto create(ProductDto dto) {
        Product product = mapper.toEntity(dto);
        return mapper.toDto(repository.save(product));
    }

    public ProductDto update(UUID id, ProductDto dto) {
        Product product = findById(id);
        product = mapper.update(product, dto);
        return mapper.toDto(repository.save(product));
    }

    public List<ProductDto> getHistory(UUID id) {
        List<Product> versions = repository.getAllVersions(id).stream()
            .sorted(Comparator.comparing(Product::getLastModifiedInstant))
            .toList();
        return mapper.toListDto(versions);
    }

    public ProductDto getVersionByPeriod(UUID id, Instant startDate, Instant endDate) {
        List<Product> versions = repository.getVersionsByPeriod(id, startDate, endDate);

        if (versions.size() > 1) {
            throw new ConstraintViolationException(String.format(VERSIONS_FIND_EXCEPTION, id), null);
        } else if (versions.isEmpty()) {
            throw new ConstraintViolationException(String.format(VERSION_NOT_FOUND_PERIOD_EXCEPTION, id), null);
        } else {
            return mapper.toDto(versions.get(0));
        }
    }

    public ProductDto rollbackVersion(UUID id, Integer version) {
        Product currentVersion = findById(id);
        int rollbackVersion = version == null ? currentVersion.getVersion() - 1 : version;

        Product product = repository.getPreviousVersion(id, rollbackVersion).stream()
            .findFirst()
            .orElseThrow(() -> new ConstraintViolationException(
                String.format(VERSION_NOT_FOUND_EXCEPTION, rollbackVersion, id),
                null)
            );
        currentVersion = mapper.rollback(currentVersion, product);
        currentVersion = repository.save(currentVersion);
        return mapper.toDto(currentVersion);
    }

    private Product findById(UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_EXCEPTION, id)));
    }

    public List<ProductDto> getAllByTariff(UUID tariffId) {
        List<Product> products = repository.getAllByTariffId(tariffId);
        return mapper.toListDto(products);
    }
}
