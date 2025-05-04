package com.bsuir.newPortalBack.service;

import com.bsuir.newPortalBack.dto.newsCategory.NewsCategoryCreateDTO;
import com.bsuir.newPortalBack.dto.newsCategory.NewsCategoryDTO;
import com.bsuir.newPortalBack.entities.NewsCategoryEntity;
import com.bsuir.newPortalBack.exception.buisness.NewsCategoryNotFoundException;
import com.bsuir.newPortalBack.mapper.NewsCategoryCreateMapper;
import com.bsuir.newPortalBack.mapper.NewsCategoryMapper;
import com.bsuir.newPortalBack.repository.NewsCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsCategoryService {

  private final NewsCategoryRepository newsCategoryRepo;
  private final NewsCategoryCreateMapper newsCategoryCreateMapper;
  private final NewsCategoryMapper newsCategoryMapper;

  @Transactional(readOnly = true)
  public List<NewsCategoryDTO> getAllCategories() {
    List<NewsCategoryEntity> categories = newsCategoryRepo.findAll();
    return newsCategoryMapper.toDTOList(
      categories
    );
  }

  @Transactional(readOnly = true)
  public NewsCategoryDTO getCategoryById(int id) {
    NewsCategoryEntity newsCategoryEntity = newsCategoryRepo.findById(id)
      .orElseThrow(() -> new NewsCategoryNotFoundException(id));
    return newsCategoryMapper.toDTO(newsCategoryEntity);
  }

  @Transactional
  public NewsCategoryDTO createCategory(NewsCategoryCreateDTO dto) {
    NewsCategoryEntity newCategory = newsCategoryCreateMapper.toEntity(dto);
    NewsCategoryEntity savedCategory = newsCategoryRepo.save(newCategory);
    return newsCategoryMapper.toDTO(savedCategory);
  }

  @Transactional
  public NewsCategoryDTO updateCategory(int id, NewsCategoryCreateDTO dto) {
    NewsCategoryEntity existingCategory = newsCategoryRepo.findById(id)
      .orElseThrow(() -> new NewsCategoryNotFoundException(id));

    existingCategory.setName(dto.getName());
    NewsCategoryEntity updatedCategory = newsCategoryRepo.save(existingCategory);
    return newsCategoryMapper.toDTO(updatedCategory);
  }

  @Transactional
  public void deleteCategory(int id) {
    if (!newsCategoryRepo.existsById(id)) {
      throw new NewsCategoryNotFoundException(id);
    }
    newsCategoryRepo.deleteById(id);
  }
}
