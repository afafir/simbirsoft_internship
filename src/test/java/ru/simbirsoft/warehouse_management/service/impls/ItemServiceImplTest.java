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
import ru.simbirsoft.warehouse_management.dto.createForms.ItemCreateDto;
import ru.simbirsoft.warehouse_management.dto.mapper.CategoryMapper;
import ru.simbirsoft.warehouse_management.dto.mapper.ItemMapper;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.Category;
import ru.simbirsoft.warehouse_management.model.Item;
import ru.simbirsoft.warehouse_management.repository.ItemRepository;
import ru.simbirsoft.warehouse_management.service.CategoryService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

  @InjectMocks ItemServiceImpl itemService;

  @Mock ItemMapper itemMapper;
  @Mock CategoryMapper categoryMapper;
  @Mock ItemRepository itemRepository;
  @Mock CategoryService categoryService;

  private Item item1;
  private Item item2;
  private ItemCreateDto itemCreateDto;
  private ItemDto item1Dto;
  private ItemDto item2Dto;

  @BeforeEach
  public void beforeTest() {
    itemCreateDto =
        ItemCreateDto.builder()
            .code("code")
            .description("desc")
            .name("name")
            .price(1.1F)
            .categories(Collections.singletonList(1L))
            .build();
    item1 =
        Item.builder()
            .code("code")
            .description("desc")
            .name("name")
            .category(
                Collections.singletonList(
                    Category.builder().description("desc").name("name").id(1L).build()))
            .price(1.1F)
            .build();

    item1Dto =
        ItemDto.builder()
            .id(1L)
            .categories(
                Collections.singletonList(
                    CategoryDto.builder().description("desc").name("name").id(1L).build()))
            .code("code")
            .description("desc")
            .price(1.1F)
            .name("name")
            .build();
      item2Dto =
              ItemDto.builder()
                      .code("code1")
                      .description("desc1")
                      .name("name1")
                      .price(1.1F)
                      .categories(
                              Collections.singletonList(
                                      CategoryDto.builder().description("desc").name("name").build()))
                      .build();
  }

  @Test
  void createItem() {
    Item created =
        Item.builder()
            .price(item1.getPrice())
            .name(item1.getName())
            .description(item1.getDescription())
            .id(1L)
            .build();
    Category category = Category.builder().description("desc").name("name").id(1L).build();
    CategoryDto categoryDto = CategoryDto.builder().description("desc").name("name").id(1L).build();
    created.setCategory(Collections.singletonList(category));
    Mockito.doReturn(categoryDto).when(categoryService).getCategory(1L);
    Mockito.doReturn(category).when(categoryMapper).map(categoryDto);
    Mockito.doReturn(created).when(itemRepository).save(item1);
    Mockito.doReturn(item1Dto).when(itemMapper).mapToDto(created);
    ItemDto test = itemService.createItem(itemCreateDto);
    Mockito.verify(itemRepository, Mockito.times(1)).save(item1);
    assertEquals(item1.getName(), test.getName());
  }

  @Test
  void getAllCategories() {
    List<Item> items = new ArrayList<>(Arrays.asList(item1, item2));
    Mockito.doReturn(items).when(itemRepository).findAll();
    Mockito.doReturn(Arrays.asList(item1Dto, item2Dto)).when(itemMapper).mapListDto(items);
    List<ItemDto> test = itemService.getAllItems();
    assertEquals(2, test.size());
    assertEquals("name", test.get(0).getName());
    assertEquals("name1", test.get(1).getName());
  }

  @Test
  void getItemIfExist() {
    item1.setId(1L);
    Mockito.doReturn(true).when(itemRepository).existsById(1L);
    Mockito.doReturn((item1)).when(itemRepository).getOne(1L);
    Mockito.doReturn(item1Dto).when(itemMapper).mapToDto(item1);
    ItemDto test = itemService.getItem(1L);
    assertEquals(1L, test.getId());
    assertEquals("name", test.getName());
    assertEquals("desc", test.getDescription());
  }

  @Test
  void getItemIfNotExist() {
    Mockito.doReturn(false).when(itemRepository).existsById(1L);
    assertThrows(NotFoundException.class, () -> itemService.getItem(1L));
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
    item1.setId(5L);
    Mockito.doReturn(true).when(itemRepository).existsById(item1Dto.getId());
    Mockito.doReturn(item1).when(itemRepository).getOne(item1Dto.getId());
    Mockito.doReturn(item1Dto).when(itemMapper).mapToDto(item1);
    ItemDto item = itemService.updateItem(item1Dto);
    assertEquals(item1Dto.getId(), item.getId());
    assertEquals(item1Dto.getDescription(), item1Dto.getDescription());
    assertEquals(item1Dto.getName(), item.getName());
    assertEquals(item1Dto.getPrice(), item.getPrice());
    assertEquals(item1Dto.getId(), item.getId());
  }
}
