package com.example.simplejpa.repository;

import com.example.simplejpa.data.World;
import org.springframework.data.repository.CrudRepository;

public interface WorldRepository extends CrudRepository<World, Long> {

}
