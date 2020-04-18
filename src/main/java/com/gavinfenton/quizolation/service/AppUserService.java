package com.gavinfenton.quizolation.service;

import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.repository.AppUserRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = username.contains("@")
                ? getByEmail(username)
                : getByUsername(username);

        // New hashset as there is currently no implementation of roles
        return new User(user.getEmail(), user.getPassword(), new HashSet<>());
    }

    public AppUser getByUsername(String username) {
        return appUserRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException(username, "User"));
    }

    public AppUser getByEmail(String email) {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException(email, "User"));
    }

}
