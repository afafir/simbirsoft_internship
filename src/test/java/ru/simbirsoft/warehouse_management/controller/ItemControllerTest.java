package ru.simbirsoft.warehouse_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.simbirsoft.warehouse_management.dto.CategoryDto;
import ru.simbirsoft.warehouse_management.dto.ItemDto;
import ru.simbirsoft.warehouse_management.dto.createForms.ItemCreateDto;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.Category;
import ru.simbirsoft.warehouse_management.model.Item;
import ru.simbirsoft.warehouse_management.service.ItemService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

  @Autowired MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean ItemService itemService;

  private ItemCreateDto itemCreateDto;
  private ItemDto itemDto;
  private ItemDto item1;
  private ItemDto item2;

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
        ItemDto.builder()
            .code("code")
            .description("desc")
            .name("name")
            .price(1.1F)
            .categories(
                Collections.singletonList(
                    CategoryDto.builder().description("desc").name("name").build()))
            .build();
    item2 =
        ItemDto.builder()
            .code("code1")
            .description("desc1")
            .name("name1")
            .price(1.1F)
            .categories(
                Collections.singletonList(
                    CategoryDto.builder().description("desc").name("name").build()))
            .build();
    itemDto =
        ItemDto.builder()
            .categories(
                Collections.singletonList(
                    CategoryDto.builder().description("desc").name("name").id(1L).build()))
            .code("code1")
            .description("desc1")
            .price(1.1F)
            .name("name1")
            .build();
  }

  @Test
  public void testSuccessfulladdItem() throws Exception {
    given(itemService.createItem(itemCreateDto)).willReturn(item1);
    MvcResult mvcResult =
        mockMvc
            .perform(
                post("/items/new")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(itemCreateDto)))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();
    JSONAssert.assertEquals(
        objectMapper.writeValueAsString(item1),
        mvcResult.getResponse().getContentAsString(),
        false);
  }

  @Test
  public void testEmptyFieldAddItem() throws Exception {
    itemCreateDto.setCode("");
    mockMvc
        .perform(
            post("/items/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemCreateDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testNullFieldAddItem() throws Exception {
    itemCreateDto.setCode(null);
    mockMvc
        .perform(
            post("/items/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemCreateDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getAllItems() throws Exception {
    List<ItemDto> items = Arrays.asList(item1, item2);
    given(itemService.getAllItems()).willReturn(items);
    List<ItemDto> expected = items;
    MvcResult result = mockMvc.perform(get("/items")).andReturn();
    JSONAssert.assertEquals(
        objectMapper.writeValueAsString(expected),
        result.getResponse().getContentAsString(),
        false);
  }

  @Test
  public void getOneItem() throws Exception {
    given(itemService.getItem(1L)).willReturn(item1);
    MvcResult result = mockMvc.perform(get("/items/1")).andReturn();
    ItemDto expected = item1;
    JSONAssert.assertEquals(
        objectMapper.writeValueAsString(expected),
        result.getResponse().getContentAsString(),
        false);
  }

  @Test
  public void getOneItemIfNotExists() throws Exception {
    given(itemService.getItem(1L))
        .willThrow(new NotFoundException(Item.class, "id", String.valueOf(1L)));
    mockMvc.perform(get("/items/1")).andExpect(status().isNotFound());
  }

  @Test
  public void deleteItem() throws Exception {
    mockMvc
        .perform(delete("/items/1"))
        .andExpect(status().isOk())
        .andExpect(content().string("Item was deleted successfully"));
  }

  @Test
  public void deleteCategoryIfNotExists() throws Exception {
    doThrow(new NotFoundException(Category.class, "id", String.valueOf(111L)))
        .when(itemService)
        .deleteItem(111L);
    mockMvc.perform(delete("/items/111")).andExpect(status().isNotFound());
  }

  @Test
  public void updateItemIfExists() throws Exception {
    ItemDto updated =
        ItemDto.builder()
            .id(1L)
            .name("name1")
            .code("code1")
            .description("desc1")
            .price(1.1F)
            .categories(
                Collections.singletonList(
                    CategoryDto.builder().description("desc").name("name").build()))
            .build();
    given(itemService.updateItem(itemDto)).willReturn(updated);
    MvcResult result =
        mockMvc
            .perform(
                put("/items/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(itemDto)))
            .andReturn();
    JSONAssert.assertEquals(
        objectMapper.writeValueAsString(updated), result.getResponse().getContentAsString(), false);
  }

  @Test
  public void updateItemIfNotExists() throws Exception {
    doThrow(new NotFoundException(Item.class, "id", String.valueOf(111L)))
        .when(itemService)
        .updateItem(itemDto);
    mockMvc
        .perform(
            put("/items/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemDto)))
        .andExpect(status().isNotFound());
  }
}
