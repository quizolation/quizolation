package com.gavinfenton.quizolation.mapper;

import com.gavinfenton.quizolation.dto.UserRegistrationDTO;
import com.gavinfenton.quizolation.entity.AppUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserRegistrationMapperTest {

    UserRegistrationMapper userRegistrationMapper = UserRegistrationMapper.INSTANCE;

    @Test
    public void testToUserMapsToUserFieldsFields() {
        // Given
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
        registrationDTO.setName("Not Louise");
        registrationDTO.setUsername("not.louise");
        registrationDTO.setEmail("some@email.com");
        registrationDTO.setPassword("password123");

        // When
        AppUser user = userRegistrationMapper.toUser(registrationDTO);

        // Then
        assertNull(user.getId());
        assertEquals(registrationDTO.getName(), user.getName());
        assertEquals(registrationDTO.getUsername(), user.getUsername());
        assertEquals(registrationDTO.getEmail(), user.getEmail());
        assertEquals(registrationDTO.getPassword(), user.getPassword());
    }

}
