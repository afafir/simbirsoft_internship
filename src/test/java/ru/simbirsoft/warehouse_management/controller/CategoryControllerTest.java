package ru.simbirsoft.warehouse_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.simbirsoft.warehouse_management.dto.CategoryDto;
import ru.simbirsoft.warehouse_management.dto.createForms.CategoryCreateDto;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.Category;
import ru.simbirsoft.warehouse_management.service.impls.CategoryServiceImpl;
import java.util.Arrays;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

  @Autowired MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean CategoryServiceImpl categoryServiceImpl;

  @Test
  public void testSuccessfulladdcategory() throws Exception {
    CategoryCreateDto categoryCreateDto =
        CategoryCreateDto.builder().description("desc").name("name").build();
    given(categoryServiceImpl.createCategory(categoryCreateDto))
        .willReturn(new CategoryDto(1L, "name", "desc"));
    mockMvc
        .perform(
            post("/categories/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryCreateDto)))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  public void testEmptyFieldsAddCategory() throws Exception {
    CategoryCreateDto categoryCreateDto =
        CategoryCreateDto.builder().description("").name("name").build();
    mockMvc
        .perform(
            post("/categories/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryCreateDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testNullFieldsAddCategory() throws Exception {
    CategoryCreateDto categoryCreateDto =
        CategoryCreateDto.builder().description("").name("name").build();
    mockMvc
        .perform(
            post("/categories/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryCreateDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getAllCategory() throws Exception {
    List<CategoryDto> categoryList =
        Arrays.asList(new CategoryDto(1L, "name", "desc"), new CategoryDto(2L, "name1", "desc1"));
    given(categoryServiceImpl.getAllCategories()).willReturn(categoryList);
    List<CategoryDto> expected = categoryList;
    MvcResult result = mockMvc.perform(get("/categories")).andReturn();
    JSONAssert.assertEquals(
        objectMapper.writeValueAsString(expected),
        result.getResponse().getContentAsString(),
        false);
  }

  @Test
  public void getOneCategory() throws Exception {
    given(categoryServiceImpl.getCategory(1L)).willReturn(new CategoryDto(1L, "name", "desc"));
    MvcResult result = mockMvc.perform(get("/categories/1")).andReturn();
    CategoryDto expected = new CategoryDto(1L, "name", "desc");
    JSONAssert.assertEquals(
        objectMapper.writeValueAsString(expected),
        result.getResponse().getContentAsString(),
        false);
  }

  @Test
  public void getOneCategoryIfNotExists() throws Exception {
    given(categoryServiceImpl.getCategory(1L))
        .willThrow(new NotFoundException(Category.class, "id", String.valueOf(1L)));
    mockMvc.perform(get("/categories/1")).andExpect(status().isNotFound());
  }

  @Test
  public void deleteCategory() throws Exception {
    mockMvc
        .perform(delete("/categories/1"))
        .andExpect(status().isOk())
        .andExpect(content().string("Category was deleted successfully"));
  }

  @Test
  public void deleteCategoryIfNotExists() throws Exception {
    doThrow(new NotFoundException(Category.class, "id", String.valueOf(111L)))
        .when(categoryServiceImpl)
        .deleteCategory(111L);
    mockMvc.perform(delete("/categories/111")).andExpect(status().isNotFound());
  }

  @Test
  public void updateCategoryIfNotExists() throws Exception {
    CategoryDto categoryDto =
        CategoryDto.builder().id(115L).description("desc").name("name").build();
    doThrow(new NotFoundException(Category.class, "id", String.valueOf(111L)))
        .when(categoryServiceImpl)
        .updateCategory(categoryDto);
    mockMvc
        .perform(
            put("/categories/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)))
        .andExpect(status().isNotFound());
  }

  @Test
  public void updateCategoryIfExists() throws Exception {
    CategoryDto categoryDto =
        CategoryDto.builder().id(115L).description("desc").name("name").build();
    CategoryDto updated = new CategoryDto(115L, "name", "desc");
    given(categoryServiceImpl.updateCategory(categoryDto)).willReturn(updated);
    MvcResult result =
        mockMvc
            .perform(
                put("/categories/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(categoryDto)))
            .andReturn();
    JSONAssert.assertEquals(
        objectMapper.writeValueAsString(updated), result.getResponse().getContentAsString(), false);
  }
}
