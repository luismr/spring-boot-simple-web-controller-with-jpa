package com.example.simplejpa;

import com.example.simplejpa.controller.WorldController;
import com.example.simplejpa.data.World;
import com.example.simplejpa.repository.WorldRepository;
import com.example.simplejpa.service.WorldService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WorldController.class)
public class WorldControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WorldService worldService;

    @MockBean
    private WorldRepository worldRepository;

    @BeforeEach
    public void beforeEach() {
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

        World earth = new World(3L, "Earth");
        World pluto = new World(9L, "Pluto");
        World plutoPlusPlus = new World(9L, "Pluto++");

        given(worldService.getWorlds()).willReturn(worlds);
        given(worldService.getWorldRepository()).willReturn(worldRepository);
        given(worldService.getWorldRepository().findById(3L)).willReturn(Optional.of(earth));
        given(worldService.getWorldRepository().findById(9L)).willReturn(Optional.of(pluto));
        given(worldService.getWorldRepository().save(pluto)).willReturn(pluto);
        given(worldService.getWorldRepository().save(plutoPlusPlus)).willReturn(plutoPlusPlus);
    }

    @Test
    public void givenListOfWorldsThenReturnsJsonArray() throws Exception {
        mvc.perform(get("/worlds").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)));
    }

    @Test
    public void givenListOfWorldsThenReturnsMercuryInFirstPlace() throws Exception {
        mvc.perform(get("/worlds").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)))
                .andExpect(jsonPath("$[0].name", hasToString("Mercury")));
    }

    @Test
    public void givenListOfWorldsThenReturnsEarthInThirdPlace() throws Exception {
        mvc.perform(get("/worlds").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)))
                .andExpect(jsonPath("$[2].name", hasToString("Earth")));
    }

    @Test
    public void givenEarthById() throws Exception {
        mvc.perform(get("/world/{id}","3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", hasToString("Earth")));
    }

    @Test
    public void createPluto() throws Exception {
        mvc.perform(post("/world")
                        .content(
                                asJsonString(
                                        new World(9L, "Pluto")
                                )
                        )
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", hasToString("Pluto")));
    }

    @Test
    public void updatePluto() throws Exception {
        mvc.perform(put("/world/{id}", 9L)
                        .content(
                                asJsonString(
                                        new World(9L, "Pluto++")
                                )
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", hasToString("Pluto++")));
    }

    @Test
    public void deletePluto() throws Exception {
        mvc.perform(delete("/world/{id}", 9L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private String asJsonString(World world) {
        try {
            return new ObjectMapper().writeValueAsString(world);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
