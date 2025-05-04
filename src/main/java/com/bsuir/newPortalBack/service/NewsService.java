package com.bsuir.newPortalBack.service;

import com.bsuir.newPortalBack.dto.news.NewsCreateDTO;
import com.bsuir.newPortalBack.dto.news.NewsDTO;
import com.bsuir.newPortalBack.entities.NewsCategoryEntity;
import com.bsuir.newPortalBack.entities.NewsEntity;
import com.bsuir.newPortalBack.entities.TagEntity;
import com.bsuir.newPortalBack.entities.UserEntity;
import com.bsuir.newPortalBack.enums.NewsStatus;
import com.bsuir.newPortalBack.exception.buisness.NewsCategoryNotFoundException;
import com.bsuir.newPortalBack.exception.buisness.NewsNotFoundException;
import com.bsuir.newPortalBack.exception.buisness.UserNotFoundException;
import com.bsuir.newPortalBack.mapper.NewsCreateMapper;
import com.bsuir.newPortalBack.mapper.NewsMapper;
import com.bsuir.newPortalBack.repository.NewsCategoryRepository;
import com.bsuir.newPortalBack.repository.NewsRepository;
import com.bsuir.newPortalBack.repository.TagRepository;
import com.bsuir.newPortalBack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {
  private final NewsRepository newsRepo;
  private final NewsMapper newsMapper;
  private final NewsCreateMapper newsCreateMapper;
  private final TagRepository tagRepo;
  private final UserRepository userRepository;
  private final NewsCategoryRepository categoryRepository;

  @Transactional
  public NewsDTO createNews(NewsCreateDTO createDTO) {
    UserEntity author = userRepository.findById(createDTO.getAuthorId())
      .orElseThrow(() -> new UserNotFoundException(createDTO.getAuthorId()));

    NewsCategoryEntity category = categoryRepository.findById(createDTO.getCategoryId())
      .orElseThrow(() -> new NewsCategoryNotFoundException(createDTO.getCategoryId()));

    NewsEntity news = newsCreateMapper.toEntity(createDTO);
    news.setAuthor(author);
    news.setCategory(category);

    List<TagEntity> tags = createOrGetTags(createDTO.getTags());
    news.setTags(tags);

    NewsEntity savedNews = newsRepo.save(news);
    return newsMapper.toDTO(savedNews);
  }

  private List<TagEntity> createOrGetTags(List<String> tagNames) {
    return tagNames.stream()
      .map(String::trim)
      .filter(name -> !name.isEmpty())
      .map(name -> {
        String normalizedName = name.toLowerCase();
        return tagRepo.findByNameCaseInsensitive(normalizedName)
          .orElseGet(() -> tagRepo.save(
            TagEntity.builder().name(name).build()
          ));
      })
      .distinct()
      .collect(Collectors.toList());
  }

  @Transactional
  public void deleteNews(int id) {
    if (!newsRepo.existsById(id)) {
      throw new NewsNotFoundException(id);
    }
    newsRepo.deleteById(id);
  }

  @Transactional(readOnly = true)
  public List<NewsDTO> getAllNews() {
    return newsMapper.toDTOList(newsRepo.findAll());
  }

  @Transactional(readOnly = true)
  public List<NewsDTO> getNewsByCategory(int categoryId) {
    return newsMapper.toDTOList(newsRepo.findByCategory_Id(categoryId));
  }

  @Transactional(readOnly = true)
  public NewsDTO getNewsById(int id) {
    return newsRepo.findById(id)
      .map(newsMapper::toDTO)
      .orElseThrow(() -> new NewsNotFoundException(id));
  }

  @Transactional(readOnly = true)
  public List<NewsDTO> getNewsByStatus(NewsStatus status) {
    return newsMapper.toDTOList(newsRepo.findByStatus(status));
  }

  @Transactional(readOnly = true)
  public List<NewsDTO> getNewsByCategoryAndStatus(int categoryId, NewsStatus status) {
    return newsMapper.toDTOList(
      newsRepo.findByCategory_IdAndStatus(categoryId, status)
    );
  }
}
