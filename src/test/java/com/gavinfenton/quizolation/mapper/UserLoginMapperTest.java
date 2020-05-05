package com.gavinfenton.quizolation.mapper;

import com.gavinfenton.quizolation.dto.UserLoginDTO;
import com.gavinfenton.quizolation.entity.AppUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserLoginMapperTest {

    UserLoginMapper userLoginMapper = UserLoginMapper.INSTANCE;

    @Test
    public void testToUserMapsToUserFieldsFields() {
        // Given
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setEmail("some@email.com");
        loginDTO.setPassword("not.louise");

        // When
        AppUser user = userLoginMapper.toUser(loginDTO);

        // Then
        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getUsername());
        assertEquals(loginDTO.getEmail(), user.getEmail());
        assertEquals(loginDTO.getPassword(), user.getPassword());
    }

}
