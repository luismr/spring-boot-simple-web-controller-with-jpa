package com.example.simplejpa.controller;

import com.example.simplejpa.data.World;
import com.example.simplejpa.service.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class WorldController {
    private final WorldService worldService;

    @Autowired
    public WorldController(WorldService worldService) {
        this.worldService = worldService;
    }

    @GetMapping(path = "/worlds", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<World> getWorlds() {
        return worldService.getWorlds();
    }

    @GetMapping(path = "/world/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public World findById(@PathVariable final Long id) {
        Optional<World> world = worldService.getWorldRepository().findById(id);
        return world.orElseThrow();
    }

    @PostMapping(path = "/world", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public World create(@RequestBody final World payload) {
        World created = worldService.getWorldRepository().save(payload);
        return created;
    }

    @PutMapping(path = "/world/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public World update(@PathVariable final Long id, @RequestBody final World payload) {
        Optional<World> w = worldService.getWorldRepository().findById(id);
        World world = w.orElseThrow();
        world.setName(payload.getName());

        World updated = worldService.getWorldRepository().save(world);
        return updated;
    }

    @DeleteMapping(path = "/world/{id}")
    public void update(@PathVariable final Long id) {
        Optional<World> w = worldService.getWorldRepository().findById(id);
        World world = w.orElseThrow();
        worldService.getWorldRepository().delete(world);
    }

}
