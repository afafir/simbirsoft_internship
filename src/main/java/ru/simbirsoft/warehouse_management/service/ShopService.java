package ru.simbirsoft.warehouse_management.service;

import ru.simbirsoft.warehouse_management.dto.ShopDto;
import ru.simbirsoft.warehouse_management.dto.createForms.ShopCreateDto;

import java.util.List;

public interface ShopService {

    ShopDto createShop(ShopCreateDto shopCreateDto);

    ShopDto getById(Long id);

    List<ShopDto> getAll();

    void deleteById(Long id);

    ShopDto getByName(String name);

}
