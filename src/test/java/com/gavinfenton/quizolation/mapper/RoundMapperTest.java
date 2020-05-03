package com.gavinfenton.quizolation.mapper;

import com.gavinfenton.quizolation.dto.RoundDTO;
import com.gavinfenton.quizolation.entity.Round;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RoundMapperTest {

    RoundMapper roundMapper = RoundMapper.INSTANCE;

    @Test
    public void testToDTOMapsToAllDTOFields() {
        // Given
        Round round = new Round();
        round.setId(123L);
        round.setName("What is?");
        round.setDescription("IDK");

        // When
        RoundDTO dto = roundMapper.toDTO(round);

        // Then
        assertEquals(round.getId(), dto.getId());
        assertEquals(round.getName(), dto.getName());
        assertEquals(round.getDescription(), dto.getDescription());
    }

    @Test
    public void testToRoundMapsFromAllDTOFields() {
        // Given
        RoundDTO dto = new RoundDTO();
        dto.setId(123L);
        dto.setName("What is?");
        dto.setDescription("IDK");

        // When
        Round round = roundMapper.toRound(dto);

        // Then
        assertEquals(dto.getId(), round.getId());
        assertNull(round.getQuizId());
        assertEquals(dto.getName(), round.getName());
        assertEquals(dto.getDescription(), round.getDescription());
    }

    @Test
    public void testToDTOListMapsToAllDTOFields() {
        // Given
        Round round = new Round();
        round.setId(123L);
        round.setName("What is?");
        round.setDescription("IDK");
        List<Round> roundList = Collections.singletonList(round);

        // When
        List<RoundDTO> dtoList = roundMapper.toDTOList(roundList);
        RoundDTO dto = dtoList.get(0);

        // Then
        assertEquals(1, dtoList.size());
        assertEquals(round.getId(), dto.getId());
        assertEquals(round.getName(), dto.getName());
        assertEquals(round.getDescription(), dto.getDescription());
    }

}
