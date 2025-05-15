package com.bsuir.newPortalBack.service;

import com.bsuir.newPortalBack.dto.tag.TagCreateDTO;
import com.bsuir.newPortalBack.dto.tag.TagDTO;
import com.bsuir.newPortalBack.entities.TagEntity;
import com.bsuir.newPortalBack.exception.buisness.TagNotFoundException;
import com.bsuir.newPortalBack.mapper.TagCreateMapper;
import com.bsuir.newPortalBack.mapper.TagMapper;
import com.bsuir.newPortalBack.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
  private final TagRepository tagRepository;
  private final TagMapper tagMapper;
  private final TagCreateMapper tagCreateMapper;

  @Transactional(readOnly = true)
  public List<TagDTO> getAllTags() {
    List<TagEntity> tags = tagRepository.findAll();
    return tagMapper.toDTOList(tags);
  }

  @Transactional(readOnly = true)
  public TagDTO getTagById(int id) {
    TagEntity tag = tagRepository.findById(id)
      .orElseThrow(() -> new TagNotFoundException(id));
    return tagMapper.toDTO(tag);
  }

  @Transactional
  public TagDTO createTag(TagCreateDTO tagCreateDTO) {
    TagEntity tag = tagCreateMapper.toEntity(tagCreateDTO);
    TagEntity createdTag = tagRepository.save(tag);
    return tagMapper.toDTO(createdTag);
  }

  @Transactional(readOnly = true)
  public List<TagDTO> getLast3Tags() {
    List<TagEntity> tags = tagRepository.findTop3ByOrderByIdDesc();
    return tagMapper.toDTOList(tags);
  }
}
