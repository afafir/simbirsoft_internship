package ru.simbirsoft.warehouse_management.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simbirsoft.warehouse_management.dto.ItemDto;
import ru.simbirsoft.warehouse_management.dto.createForms.ItemCreateDto;
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
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryService categoryService;

    @Override
    public Item createItem(ItemCreateDto itemCreateDto) {
        Item item = Item.builder()
                .code(itemCreateDto.getCode())
                .description(itemCreateDto.getDescription())
                .price(itemCreateDto.getPrice())
                .name(itemCreateDto.getName())
                .build();
        List<Category> itemCategories = itemCreateDto.getCategories().stream()
                .map(x -> categoryService.getCategory(x)).collect(Collectors.toList());
        item.setCategory(itemCategories);
        return itemRepository.save(item);
    }

    @Override
    public void deleteItem(Long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
        } else throw new NotFoundException(Item.class, "id", String.valueOf(id));

    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItem(Long id) {
        if (itemRepository.existsById(id)) {
            return itemRepository.findById(id).get();
        } else throw new NotFoundException(Item.class, "id", String.valueOf(id));
    }
    @Transactional
    @Override
    public Item updateItem(ItemDto itemDto) {
        if (itemRepository.existsById(itemDto.getId())) {
            Item item = itemRepository.getOne(itemDto.getId());
            item.setCode(itemDto.getCode());
            item.setDescription(itemDto.getDescription());
            item.setName(itemDto.getName());
            item.setPrice(itemDto.getPrice());
            List<Category> itemCategories = itemDto.getCategory().stream()
                    .map(x -> categoryService.getCategory(x.getId())).collect(Collectors.toList());
            item.setCategory(itemCategories);
            return item;
        } else throw new NotFoundException(Item.class, "id", String.valueOf(itemDto.getId()));
    }
}
