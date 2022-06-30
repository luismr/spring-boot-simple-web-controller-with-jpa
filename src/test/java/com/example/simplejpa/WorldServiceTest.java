package com.example.simplejpa;

import com.example.simplejpa.data.World;
import com.example.simplejpa.service.WorldService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class WorldServiceTest {

    @MockBean
    private WorldService worldService;

    @BeforeEach
    public void setupEach() {
        List<World> worlds = Arrays.asList(
                new World(1L, "Mercury"),
                new World(2L, "Venus"),
                new World(3L, "Earth"),
                new World(4L, "Mars"),
                new World(5L, "Jupiter"),
                new World(6L, "Saturn"),
                new World(7L, "Uranus"),
                new World(8L, "Neptune")
        );

        given(worldService.getWorlds()).willReturn(worlds);

        given(worldService.exists("Mercury")).willReturn(true);
        given(worldService.exists("Venus")).willReturn(true);
        given(worldService.exists("Earth")).willReturn(true);
        given(worldService.exists("Mars")).willReturn(true);
        given(worldService.exists("Jupiter")).willReturn(true);
        given(worldService.exists("Saturn")).willReturn(true);
        given(worldService.exists("Uranus")).willReturn(true);
        given(worldService.exists("Neptune")).willReturn(true);
    }

    @Test
    public void ensureWorldListIsNotEmpty() {
        assertNotNull(worldService.getWorlds());
    }

    @Test
    public void ensureWorldListHasAllPlanets() {
        assertTrue(worldService.exists("Mercury"));
        assertTrue(worldService.exists("Venus"));
        assertTrue(worldService.exists("Earth"));
        assertTrue(worldService.exists("Mars"));
        assertTrue(worldService.exists("Jupiter"));
        assertTrue(worldService.exists("Saturn"));
        assertTrue(worldService.exists("Uranus"));
        assertTrue(worldService.exists("Neptune"));
    }

    @Test
    public void ensureWorldListHasntAnInvalidPlanets() {
        assertFalse(worldService.exists("Pluto"));
    }
}
