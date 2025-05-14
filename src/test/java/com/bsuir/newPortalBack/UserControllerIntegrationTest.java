package com.bsuir.newPortalBack;

import com.bsuir.newPortalBack.dto.user.UserRegistrationDTO;
import com.bsuir.newPortalBack.entities.RoleEntity;
import com.bsuir.newPortalBack.entities.UserEntity;
import com.bsuir.newPortalBack.entities.UserInfoEntity;
import com.bsuir.newPortalBack.enums.UserRole;
import com.bsuir.newPortalBack.repository.RoleRepository;
import com.bsuir.newPortalBack.repository.UserRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private ObjectMapper objectMapper;

  private UserEntity testUser;

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
    roleRepository.deleteAll();

    RoleEntity adminRole = new RoleEntity();
    adminRole.setName(UserRole.ADMIN);
    roleRepository.save(adminRole);

    RoleEntity userRole = new RoleEntity();
    userRole.setName(UserRole.USER);
    roleRepository.save(userRole);

    UserInfoEntity userInfo = new UserInfoEntity.Builder()
      .lastName("Doe")
      .firstName("John")
      .surname("Smith")
      .position("Developer")
      .department("IT")
      .build();

    testUser = UserEntity.builder()
      .username("testuser")
      .email("test@example.com")
      .isApproved(true)
      .userInfo(userInfo)
      .roles(new HashSet<>(Set.of(roleRepository.findByName(UserRole.USER).get())))
      .build();
    testUser.setPasswordHash("$2a$12$iBKous6JtY0tPBQ8.AA6Y.z7SxIZOEaKP17RDBfkHUA9anAAW8Dr6");

    userInfo.setUser(testUser);
    testUser = userRepository.save(testUser);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void getAllUsers_AsAdmin_ReturnsUsersList() throws Exception {
    mockMvc.perform(get("/users"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.length()").value(1))
      .andExpect(jsonPath("$.data[0].username").value("testuser"));
  }

  @Test
  @WithMockUser(roles="ADMIN")
  void getExistingUserById_ReturnsUser() throws Exception {
    mockMvc.perform(get("/users/{id}", testUser.getId()))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.username").value("testuser"));
  }

  @Test
  @WithMockUser(roles="ADMIN")
  void getNonExistingUserById_ReturnsNotFound() throws Exception {
    mockMvc.perform(get("/users/999"))
      .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void createValidUser_AsAdmin_ReturnsCreatedUser() throws Exception {
    UserRegistrationDTO newUser = UserRegistrationDTO.builder()
      .username("newuser")
      .email("new@example.com")
      .password("pass")
      .lastName("New")
      .firstName("User")
      .surname("Middle")
      .position("Position")
      .department("Department")
      .build();

    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.data.username").value("newuser"));

    assertEquals(2, userRepository.count());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void createUserWithExistingUsername_ReturnsConflict() throws Exception {
    UserRegistrationDTO existingUser = UserRegistrationDTO.builder()
      .username("testuser")
      .email("new@example.com")
      .password("pass")
      .lastName("New")
      .firstName("User")
      .surname("Middle")
      .position("Position")
      .department("Department")
      .build();

    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(existingUser)))
      .andExpect(status().isConflict());
  }

  @Test
  @WithMockUser
  void updateUser_WithValidData_ReturnsUpdatedUser() throws Exception {
    UserRegistrationDTO updateDTO = UserRegistrationDTO.builder()
      .username("updateduser")
      .email("updated@example.com")
      .password("newpass")
      .lastName("Updated")
      .firstName("User")
      .surname("Middle")
      .position("New Position")
      .department("New Department")
      .build();

    mockMvc.perform(put("/users/{id}", testUser.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateDTO)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.username").value("updateduser"));

    UserEntity updatedUser = userRepository.findById(testUser.getId()).get();
    assertEquals("New Position", updatedUser.getUserInfo().getPosition());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void deleteExistingUser_AsAdmin_ReturnsSuccess() throws Exception {
    mockMvc.perform(delete("/users/{id}", testUser.getId()))
      .andExpect(status().isOk());

    assertFalse(userRepository.existsById(testUser.getId()));
  }

  @Test
  @WithMockUser
  void updateUserField_ValidField_ReturnsSuccess() throws Exception {
    String jsonRequest = "{ \"firstName\": \"UpdatedName\" }";

    mockMvc.perform(patch("/users/{id}", testUser.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonRequest))
      .andExpect(status().isOk());

    UserEntity updatedUser = userRepository.findById(testUser.getId()).get();
    assertEquals("UpdatedName", updatedUser.getUserInfo().getFirstName());
  }

  @Test
  @WithMockUser
  void updateUserField_InvalidField_ReturnsBadRequest() throws Exception {
    String jsonRequest = "{ \"invalidField\": \"value\" }";

    mockMvc.perform(patch("/users/{id}", testUser.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonRequest))
      .andExpect(status().isBadRequest());
  }
}