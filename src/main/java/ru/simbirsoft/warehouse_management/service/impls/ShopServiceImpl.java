package ru.simbirsoft.warehouse_management.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.simbirsoft.warehouse_management.dto.ShopDto;
import ru.simbirsoft.warehouse_management.dto.createForms.ShopCreateDto;
import ru.simbirsoft.warehouse_management.dto.mapper.ShopMapper;
import ru.simbirsoft.warehouse_management.dto.mapper.WarehouseMapper;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.Shop;
import ru.simbirsoft.warehouse_management.model.Supplier;
import ru.simbirsoft.warehouse_management.model.Warehouse;
import ru.simbirsoft.warehouse_management.repository.ShopRepository;
import ru.simbirsoft.warehouse_management.service.ShopService;
import ru.simbirsoft.warehouse_management.service.WarehouseService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

  // mappers
  private final ShopMapper shopMapper;
  private final WarehouseMapper warehouseMapper;

  // services
  private final WarehouseService warehouseService;

  // repository
  private final ShopRepository shopRepository;

  @Override
  public ShopDto createShop(ShopCreateDto shopCreateDto) {
    Warehouse warehouse =
        warehouseMapper.map(warehouseService.getById(shopCreateDto.getWarehouseId()));
    Shop shop = Shop.builder()
            .name(shopCreateDto.getName())
            .warehouse(warehouse)
            .build();
    return shopMapper.mapToDto(shopRepository.save(shop));
  }

  @Override
  public ShopDto getById(Long id) {
    if (shopRepository.existsById(id)) {
      return shopMapper.mapToDto(shopRepository.getOne(id));
    } else throw new NotFoundException(Shop.class, "id", String.valueOf(id));
  }

  @Override
  public List<ShopDto> getAll() {
    return shopMapper.mapToListDto(shopRepository.findAll());
  }

  @Override
  public void deleteById(Long id) {
    if (shopRepository.existsById(id)) {
      shopRepository.deleteById(id);
    } else throw new NotFoundException(Shop.class, "id", String.valueOf(id));
  }

  @Override
  public ShopDto getByName(String name) {
    Optional<Shop> shopOptional = shopRepository.findByName(name);
    if (shopOptional.isPresent()) {
      return shopMapper.mapToDto(shopOptional.get());
    } else throw new NotFoundException(Shop.class, "name", name);
  }
}
