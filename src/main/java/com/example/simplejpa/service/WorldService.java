package com.example.simplejpa.service;

import com.example.simplejpa.data.World;
import com.example.simplejpa.repository.WorldRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorldService {

    @Getter
    private final WorldRepository worldRepository;

    @Autowired
    public WorldService(WorldRepository worldRepository) {
        this.worldRepository = worldRepository;
    }

    public Iterable<World> getWorlds() {
        return worldRepository.findAll();
    }

    public boolean exists(final String name) {
        List<World> worlds = new ArrayList<>();
        this.getWorlds().forEach(worlds::add);

        return worlds.stream().filter(w -> name.equalsIgnoreCase(w.getName())).count() > 0;
    }

}
