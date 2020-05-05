package com.gavinfenton.quizolation.service;

import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.repository.AppUserRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AppUserService {

    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;

    public AppUserService(PasswordEncoder passwordEncoder, AppUserRepository appUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepository = appUserRepository;
    }

    public AppUser getUserByEmail(String email) {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException(email, "User"));
    }

    public void registerUser(AppUser user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        appUserRepository.save(user);
    }

    public AppUser loginUser(AppUser userLogin) {
        AppUser existingUser = getUserByEmail(userLogin.getUsername());

        if (!passwordEncoder.matches(userLogin.getPassword(), existingUser.getPassword())) {
            throw new ObjectNotFoundException(userLogin.getUsername(), "User");
        }

        UserDetails userDetails = new User(existingUser.getUsername(), existingUser.getPassword(), new HashSet<>());

        Authentication authentication = new UsernamePasswordAuthenticationToken(existingUser, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return existingUser;
    }

}
