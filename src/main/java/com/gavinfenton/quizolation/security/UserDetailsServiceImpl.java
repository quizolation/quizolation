package com.gavinfenton.quizolation.security;

import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.repository.AppUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = optionalAppUser(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        // New hashset as there is currently no implementation of roles
        return new User(user.getEmail(), user.getPassword(), new HashSet<>());
    }

    Optional<AppUser> optionalAppUser(String username) {
        return username.contains("@")
                ? appUserRepository.findByEmail(username)
                : appUserRepository.findByUsername(username);
    }

}
