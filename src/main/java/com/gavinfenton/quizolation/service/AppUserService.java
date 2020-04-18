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

    public AppUser getByUsername(String username) {
        return appUserRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException(username, "User"));
    }

    public AppUser getByEmail(String email) {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException(email, "User"));
    }

    public AppUser registerUser(AppUser user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        return appUserRepository.save(user);
    }

    public AppUser loginUser(AppUser user) {
        AppUser existingUser = getByEmail(user.getEmail());

        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            // TODO: throw
            return null;
        }

        UserDetails userDetails = new User(existingUser.getUsername(), existingUser.getPassword(), new HashSet<>());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return existingUser;
    }

    public AppUser getUser(String username) {
        return getByUsername(username);
    }

}
