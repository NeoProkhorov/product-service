package com.neoflex.prokhorov.product_service.web;

import com.neoflex.prokhorov.product_service.service.ProductService;
import com.neoflex.prokhorov.product_service.service.dto.ProductDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductController {
    @Autowired
    ProductService service;

    @GetMapping
    List<ProductDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    ProductDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    ProductDto create(@RequestBody ProductDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/{id}")
    ProductDto update(@PathVariable UUID id, @RequestBody ProductDto dto) {
        return service.update(id, dto);
    }

    @GetMapping("/history/{id}")
    List<ProductDto> getHistory(@PathVariable UUID id) {
        return service.getHistory(id);
    }

    @GetMapping("/history/by-period/{id}")
    ProductDto getVersionByPeriod(
        @PathVariable UUID id,
        @RequestParam Instant startDate,
        @RequestParam Instant endDate
    ) {
        return service.getVersionByPeriod(id, startDate, endDate);
    }

    @PutMapping("/history/rollback/{id}")
    ProductDto rollbackVersion(@PathVariable UUID id, @RequestParam(required = false) Integer version) {
        return service.rollbackVersion(id, version);
    }

    @GetMapping("/by-tariff")
    List<ProductDto> getAllByTariff(@RequestParam UUID tariffId) {
        return service.getAllByTariff(tariffId);
    }
}
