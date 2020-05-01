package com.gavinfenton.quizolation.service;

import com.gavinfenton.quizolation.dto.UserDetailsDTO;
import com.gavinfenton.quizolation.dto.UserLoginDTO;
import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.mapper.UserDetailsMapper;
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
    private final UserDetailsMapper userDetailsMapper = UserDetailsMapper.INSTANCE;

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

    public UserDetailsDTO registerUser(AppUser user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        user = appUserRepository.save(user);

        return userDetailsMapper.toUserDetailsDTO(user);
    }

    public UserDetailsDTO loginUser(UserLoginDTO userLogin) {
        AppUser existingUser = userLogin.getUsername().contains("@")
                ? getByEmail(userLogin.getUsername())
                : getByUsername(userLogin.getUsername());

        if (!passwordEncoder.matches(userLogin.getPassword(), existingUser.getPassword())) {
            throw new ObjectNotFoundException(userLogin.getUsername(), "User");
        }

        UserDetails userDetails = new User(existingUser.getUsername(), existingUser.getPassword(), new HashSet<>());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return userDetailsMapper.toUserDetailsDTO(existingUser);
    }

    public UserDetailsDTO getUser(String username) {
        return userDetailsMapper.toUserDetailsDTO(getByUsername(username));
    }

}
