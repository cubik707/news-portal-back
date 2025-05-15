package com.bsuir.newPortalBack;

import com.bsuir.newPortalBack.dto.newsCategory.NewsCategoryCreateDTO;
import com.bsuir.newPortalBack.dto.newsCategory.NewsCategoryDTO;
import com.bsuir.newPortalBack.entities.NewsCategoryEntity;
import com.bsuir.newPortalBack.repository.NewsCategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NewsCategoriesControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private NewsCategoryRepository newsCategoryRepository;

  @Autowired
  private ObjectMapper objectMapper;

  private NewsCategoryDTO testCategory;

  @BeforeEach
  void setUp() {
    newsCategoryRepository.deleteAll();
    testCategory = createTestCategory("Test Category");
  }

  private NewsCategoryDTO createTestCategory(String name) {
    return NewsCategoryDTO.builder()
      .id(1)
      .name(name)
      .build();
  }

  private NewsCategoryEntity createTestEntity(String name) {
    return NewsCategoryEntity.builder()
      .name(name)
      .newsList(new ArrayList<>())
      .subscribers(new HashSet<>())
      .build();
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void getAllCategories_ReturnsAllCategories() throws Exception {
    // Given
    newsCategoryRepository.saveAll(List.of(
      createTestEntity("Category 1"),
      createTestEntity("Category 2")
    ));

    // When & Then
    mockMvc.perform(get("/categories"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.length()").value(2))
      .andExpect(jsonPath("$.data[0].name").value("Category 1"))
      .andExpect(jsonPath("$.data[1].name").value("Category 2"));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void getCategoryById_ExistingId_ReturnsCategory() throws Exception {
    // Given
    NewsCategoryEntity entity = newsCategoryRepository.save(
      createTestEntity("Test Category")
    );

    // When & Then
    mockMvc.perform(get("/categories/{id}", entity.getId()))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.name").value("Test Category"));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void getCategoryById_NonExistingId_ReturnsNotFound() throws Exception {
    mockMvc.perform(get("/categories/999"))
      .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void createCategory_ValidRequest_ReturnsCreatedCategory() throws Exception {
    // Given
    NewsCategoryCreateDTO createDTO = NewsCategoryCreateDTO.builder()
      .name("New Category")
      .build();

    // When & Then
    MvcResult result = mockMvc.perform(post("/categories")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(createDTO)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.data.name").value("New Category"))
      .andReturn();

    // Verify database
    List<NewsCategoryEntity> categories = newsCategoryRepository.findAll();
    assertEquals(1, categories.size());
    assertEquals("New Category", categories.get(0).getName());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void updateCategory_ValidRequest_ReturnsUpdatedCategory() throws Exception {
    // Given
    NewsCategoryEntity entity = newsCategoryRepository.save(
      createTestEntity("Old Name")
    );
    NewsCategoryCreateDTO updateDTO = NewsCategoryCreateDTO.builder()
      .name("New Name")
      .build();

    // When & Then
    mockMvc.perform(put("/categories/{id}", entity.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateDTO)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.name").value("New Name"));

    // Verify database
    NewsCategoryEntity updatedEntity = newsCategoryRepository.findById(entity.getId()).get();
    assertEquals("New Name", updatedEntity.getName());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void deleteCategory_ExistingId_DeletesCategory() throws Exception {
    // Given
    NewsCategoryEntity entity = newsCategoryRepository.save(
      createTestEntity("To Delete")
    );


    // When & Then
    mockMvc.perform(delete("/categories/{id}", entity.getId()))
      .andExpect(status().isOk());

    // Verify database
    assertFalse(newsCategoryRepository.existsById(entity.getId()));
  }

  @Test
  @WithMockUser
  void createCategory_WithoutAdminRole_ReturnsForbidden() throws Exception {
    NewsCategoryCreateDTO createDTO = NewsCategoryCreateDTO.builder()
      .name("New Name")
      .build();
    mockMvc.perform(post("/categories")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(createDTO)))
      .andExpect(status().isForbidden());
  }
}
