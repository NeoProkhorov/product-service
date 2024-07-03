package com.neoflex.prokhorov.product_service.value;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum ProductType {
    LOAN("Займ"),
    CARD("Карта");

    String translate;
}
