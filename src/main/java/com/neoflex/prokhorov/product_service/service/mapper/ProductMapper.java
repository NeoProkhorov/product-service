package com.neoflex.prokhorov.product_service.service.mapper;

import com.neoflex.prokhorov.product_service.domain.Product;
import com.neoflex.prokhorov.product_service.service.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "lastModifiedInstant", ignore = true)
    Product toEntity(ProductDto source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastModifiedInstant", ignore = true)
    @Mapping(target = "version", expression = "java(target.updateVersion())")
    Product update(@MappingTarget Product target, ProductDto source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastModifiedInstant", ignore = true)
    Product rollback(@MappingTarget Product target, Product source);

    default List<ProductDto> toListDto(List<Product> sourceList) {
        return sourceList.stream().map(this::toDto).toList();
    }
}
