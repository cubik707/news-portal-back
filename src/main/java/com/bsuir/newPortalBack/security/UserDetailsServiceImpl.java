package com.bsuir.newPortalBack.security;

import com.bsuir.newPortalBack.entities.UserEntity;
import com.bsuir.newPortalBack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

  private final UserRepository userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserEntity> userOptional = userRepo.findByUsername(username);
    if (userOptional.isEmpty()) {
      logger.warn("User Not Found: {}", username);
      throw new UsernameNotFoundException("User not found");
    }
    return new UserDetailsImpl(userOptional.get());
  }
}
