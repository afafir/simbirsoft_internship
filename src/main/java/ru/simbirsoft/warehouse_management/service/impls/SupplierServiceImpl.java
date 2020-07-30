package ru.simbirsoft.warehouse_management.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.warehouse_management.dto.SupplierDto;
import ru.simbirsoft.warehouse_management.dto.createForms.SupplierCreateDto;
import ru.simbirsoft.warehouse_management.dto.mapper.SupplierMapper;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.Supplier;
import ru.simbirsoft.warehouse_management.model.Warehouse;
import ru.simbirsoft.warehouse_management.repository.SupplierRepository;
import ru.simbirsoft.warehouse_management.service.SupplierService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

  private final SupplierRepository supplierRepository;

  private final SupplierMapper supplierMapper;

  @Override
  public SupplierDto createWarehouse(SupplierCreateDto supplierCreateDto) {
    Supplier supplier = Supplier.builder().name(supplierCreateDto.getName()).build();
    return supplierMapper.mapToDto(supplierRepository.save(supplier));
  }

  @Override
  public void deleteSupplier(Long id) {
    if (supplierRepository.existsById(id)) {
      supplierRepository.deleteById(id);
    } else throw new NotFoundException(Supplier.class, "id", String.valueOf(id));
  }

  @Override
  public List<SupplierDto> getAll() {
    return supplierMapper.mapListDto(supplierRepository.findAll());
  }

  @Override
  public SupplierDto getById(Long id) {
    if (supplierRepository.existsById(id)) {
      return supplierMapper.mapToDto(supplierRepository.getOne(id));
    } else throw new NotFoundException(Supplier.class, "id", String.valueOf(id));
  }

  @Override
  public SupplierDto getByName(String name) {
    Optional<Supplier> supplierOptional = supplierRepository.findByName(name);
    if (supplierOptional.isPresent()) {
      return supplierMapper.mapToDto(supplierOptional.get());
    } else throw new NotFoundException(Supplier.class, "name", name);
  }
}
