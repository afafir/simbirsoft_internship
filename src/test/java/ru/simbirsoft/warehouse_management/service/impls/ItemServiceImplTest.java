package ru.simbirsoft.warehouse_management.service.impls;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.simbirsoft.warehouse_management.dto.CategoryDto;
import ru.simbirsoft.warehouse_management.dto.ItemDto;
import ru.simbirsoft.warehouse_management.dto.createForms.CategoryCreateDto;
import ru.simbirsoft.warehouse_management.dto.createForms.ItemCreateDto;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.Category;
import ru.simbirsoft.warehouse_management.model.Item;
import ru.simbirsoft.warehouse_management.repository.CategoryRepository;
import ru.simbirsoft.warehouse_management.repository.ItemRepository;
import ru.simbirsoft.warehouse_management.service.CategoryService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @InjectMocks
    ItemServiceImpl itemService;

    @Mock
    ItemRepository itemRepository;
    @Mock
    CategoryService categoryService;

    private  Item item1;
    private  Item item2;
    private  ItemCreateDto itemCreateDto;
    private  ItemDto itemDto;

    @BeforeEach
    public void beforeTest()
    {
        itemCreateDto = ItemCreateDto.builder()
                .code("code")
                .description("desc")
                .name("name")
                .price(1.1F)
                .categories(Collections.singletonList(1L))
                .build();
        item1 = Item.builder()
                .code("code")
                .description("desc")
                .name("name")
                .category(Collections.singletonList(Category.builder().description("desc")
                        .name("name")
                        .id(1L)
                        .build()))
                .price(1.1F)
                .build();
        item2 = Item.builder()
                .code("code1")
                .description("desc1")
                .name("name1")
                .price(1.1F)
                .category(Collections.singletonList(Category.builder().description("desc")
                        .name("name")
                        .build()))
                .build();
        itemDto = ItemDto.builder()
                .id(1L)
                .category(Collections.singletonList(CategoryDto.builder().description("desc")
                        .name("name")
                        .id(1L)
                        .build()))
                .code("code1")
                .description("desc1")
                .price(1.1F)
                .name("name1")
                .build();
    }
    @Test
    void createItem() {
        Item created = Item.builder()
                .price(item1.getPrice())
                .name(item1.getName())
                .description(item1.getDescription())
                .id(1L)
                .build();
        Category category = Category.builder().description("desc")
                .name("name")
                .id(1L)
                .build();
        created.setCategory(Collections.singletonList(category));
        Mockito.doReturn(category).when(categoryService).getCategory(1L);
        Mockito.doReturn(created).when(itemRepository).save(item1);
        Item test = itemService.createItem(itemCreateDto);
        Mockito.verify(itemRepository, Mockito.times(1)).save(item1);
        assertEquals(item1.getName(), test.getName());
    }


    @Test
    void getAllCategories() {
        List<Item> items = new ArrayList<> (Arrays.asList(item1, item2));
        Mockito.doReturn(items).when(itemRepository).findAll();
        List<Item> test = itemRepository.findAll();
        assertEquals(2, test.size());
        assertEquals("name", test.get(0).getName());
        assertEquals("name1", test.get(1).getName());

    }

    @Test
    void getItemIfExist() {
        item1.setId(1L);
        Mockito.doReturn(true).when(itemRepository).existsById(1L);
        Mockito.doReturn(Optional.of(item1)).when(itemRepository).findById(1L);
        Item test = itemService.getItem(1L);
        assertEquals(1L, test.getId());
        assertEquals("name", test.getName());
        assertEquals("desc", test.getDescription());
    }

    @Test
    void getItemIfNotExist() {
        Mockito.doReturn(false).when(itemRepository).existsById(1L);
        assertThrows(NotFoundException.class, () ->itemService.getItem(1L));
    }

    @Test
    void deleteCategoryIfExist() {
        Long id = 12L;
        Mockito.doReturn(true).when(itemRepository).existsById(id);
        itemService.deleteItem(12L);
        Mockito.verify(itemRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void deleteItemIfNotExist() {
        Long id = 12L;
        Mockito.doReturn(false).when(itemRepository).existsById(id);
        assertThrows(NotFoundException.class, () -> itemService.deleteItem(id));
    }

    @Test
    void updateItemIfExist() {
        item1.setId(1L);
        Mockito.doReturn(true).when(itemRepository).existsById(itemDto.getId());
        Mockito.doReturn(item1).when(itemRepository).getOne(itemDto.getId());
        Item item = itemService.updateItem(itemDto);
        assertEquals(itemDto.getId(), item.getId());
        assertEquals(itemDto.getDescription(), itemDto.getDescription());
        assertEquals(itemDto.getName(), item.getName());
        assertEquals(itemDto.getPrice(), item.getPrice());
        assertEquals(itemDto.getId(), item.getId());
    }

}