package com.bsuir.newPortalBack;

import com.bsuir.newPortalBack.dto.user.UpdateRoleRequestDTO;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AdminControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private UserEntity testUser;
  private RoleEntity adminRole;

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
    roleRepository.deleteAll();

    // Create roles
    adminRole = new RoleEntity();
    adminRole.setName(UserRole.ADMIN);
    roleRepository.save(adminRole);

    RoleEntity userRole = new RoleEntity();
    userRole.setName(UserRole.USER);
    roleRepository.save(userRole);

    // Create admin user
    Set<RoleEntity> adminRoles = new HashSet<>();
    adminRoles.add(adminRole);
    adminRoles.add(userRole);

    UserInfoEntity adminInfo = new UserInfoEntity();
    adminInfo.setFirstName("Admin");
    adminInfo.setLastName("Adminov");
    adminInfo.setDepartment("IT");
    adminInfo.setPosition("Admin");
    adminInfo.setSurname("Adminovich");

    UserEntity testAdmin = UserEntity.builder()
      .username("admin")
      .email("admin@example.com")
      .isApproved(true)
      .userInfo(adminInfo)
      .roles(adminRoles)
      .build();
    adminInfo.setUser(testAdmin);
    testAdmin.setPasswordHash(passwordEncoder.encode("pass"));
    testAdmin = userRepository.save(testAdmin);

    // Create regular unapproved user
    Set<RoleEntity> userRoles = new HashSet<>();
    userRoles.add(userRole);

    UserInfoEntity userInfo = new UserInfoEntity();
    userInfo.setFirstName("Test");
    userInfo.setLastName("Userov");
    userInfo.setDepartment("Testing");
    userInfo.setPosition("Tester");
    userInfo.setSurname("Testovich");

    testUser = UserEntity.builder()
      .username("testuser")
      .email("user@example.com")
      .isApproved(false)
      .userInfo(userInfo)
      .roles(userRoles)
      .build();
    userInfo.setUser(testUser);
    testUser.setPasswordHash(passwordEncoder.encode("pass"));
    testUser = userRepository.save(testUser);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void approveUser_ValidRequest_ApprovesUser() throws Exception {
    // When & Then
    mockMvc.perform(patch("/users/{id}/approve", testUser.getId()))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.approved").value(true));

    UserEntity updatedUser = userRepository.findById(testUser.getId()).orElseThrow();
    assertTrue(updatedUser.isApproved());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void assignRole_ValidRole_AddsRoleToUser() throws Exception {
    // Given
    UpdateRoleRequestDTO request = new UpdateRoleRequestDTO();
    request.setRole("ADMIN");


    // When & Then
    mockMvc.perform(patch("/users/{id}/roles", testUser.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.roles.length()").value(2));

    UserEntity updatedUser = userRepository.findById(testUser.getId()).orElseThrow();
    assertTrue(updatedUser.getRoles().stream().anyMatch(r -> r.getName() == UserRole.ADMIN));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void assignRole_InvalidRole_ReturnsBadRequest() throws Exception {
    // Given
    UpdateRoleRequestDTO request = new UpdateRoleRequestDTO();
    request.setRole("INVALID_ROLE");

    // When & Then
    mockMvc.perform(patch("/users/{id}/roles", testUser.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message").value("Некорректная роль: INVALID_ROLE"));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void removeRole_ValidRole_RemovesRoleFromUser() throws Exception {
    // Given
    testUser.getRoles().add(adminRole);
    userRepository.save(testUser);
    UpdateRoleRequestDTO request = new UpdateRoleRequestDTO();
    request.setRole("ADMIN");

    // When & Then
    mockMvc.perform(delete("/users/{id}/roles", testUser.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.roles.length()").value(1));

    UserEntity updatedUser = userRepository.findById(testUser.getId()).orElseThrow();
    assertFalse(updatedUser.getRoles().stream().anyMatch(r -> r.getName() == UserRole.ADMIN));
  }

}