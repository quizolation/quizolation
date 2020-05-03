package com.gavinfenton.quizolation.mapper;

import com.gavinfenton.quizolation.dto.UserDetailsDTO;
import com.gavinfenton.quizolation.entity.AppUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDetailsMapperTest {

    UserDetailsMapper userDetailsMapper = UserDetailsMapper.INSTANCE;

    @Test
    public void testToDTOMapsToAllDTOFields() {
        // Given
        AppUser appUser = new AppUser();
        appUser.setId(123L);
        appUser.setName("Not Louise");
        appUser.setEmail("some@email.com");
        appUser.setUsername("not.louise");

        // When
        UserDetailsDTO dto = userDetailsMapper.toDTO(appUser);

        // Then
        assertEquals(appUser.getId(), dto.getId());
        assertEquals(appUser.getName(), dto.getName());
        assertEquals(appUser.getEmail(), dto.getEmail());
        assertEquals(appUser.getUsername(), dto.getUsername());
    }

}
