package ru.simbirsoft.warehouse_management.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.warehouse_management.dto.ItemDto;
import ru.simbirsoft.warehouse_management.dto.createForms.ItemCreateDto;
import ru.simbirsoft.warehouse_management.dto.mapper.CategoryMapper;
import ru.simbirsoft.warehouse_management.dto.mapper.ItemMapper;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.Category;
import ru.simbirsoft.warehouse_management.model.Item;
import ru.simbirsoft.warehouse_management.repository.ItemRepository;
import ru.simbirsoft.warehouse_management.service.CategoryService;
import ru.simbirsoft.warehouse_management.service.ItemService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

  private final ItemRepository itemRepository;

  private final CategoryService categoryService;

  private final ItemMapper itemMapper;

  private final CategoryMapper categoryMapper;

  @Override
  public ItemDto createItem(ItemCreateDto itemCreateDto) {
    Item item =
        Item.builder()
            .code(itemCreateDto.getCode())
            .description(itemCreateDto.getDescription())
            .price(itemCreateDto.getPrice())
            .name(itemCreateDto.getName())
            .build();
    List<Category> itemCategories =
        itemCreateDto.getCategories().stream()
            .map(categoryService::getCategory)
            .map(categoryMapper::map)
            .collect(Collectors.toList());
    item.setCategories(itemCategories);
    return itemMapper.mapToDto(itemRepository.save(item));
  }

  @Override
  public void deleteItem(Long id) {
    if (itemRepository.existsById(id)) {
      itemRepository.deleteById(id);
    } else throw new NotFoundException(Item.class, "id", String.valueOf(id));
  }

  @Override
  public List<ItemDto> getAllItems() {
    return itemMapper.mapListDto(itemRepository.findAll());
  }

  @Override
  public ItemDto getItem(Long id) {
    if (itemRepository.existsById(id)) {
      return itemMapper.mapToDto(itemRepository.getOne(id));
    } else throw new NotFoundException(Item.class, "id", String.valueOf(id));
  }

  @Transactional
  @Override
  public ItemDto updateItem(ItemDto itemDto) {
    if (itemRepository.existsById(itemDto.getId())) {
      Item item = itemRepository.getOne(itemDto.getId());
      item.setCode(itemDto.getCode());
      item.setDescription(itemDto.getDescription());
      item.setName(itemDto.getName());
      item.setPrice(itemDto.getPrice());
      List<Category> itemCategories =
          itemDto.getCategories().stream()
              .map(x -> categoryService.getCategory(x.getId()))
              .map(categoryMapper::map)
              .collect(Collectors.toList());
      item.setCategories(itemCategories);
      return itemMapper.mapToDto(item);
    } else throw new NotFoundException(Item.class, "id", String.valueOf(itemDto.getId()));
  }
}
