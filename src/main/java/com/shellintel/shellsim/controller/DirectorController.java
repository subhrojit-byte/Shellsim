package com.shellintel.shellsim.controller;

import com.shellintel.shellsim.model.CompanyDirector;
import com.shellintel.shellsim.model.Director;
import com.shellintel.shellsim.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/director")
public class DirectorController {

    private final DirectorRepository directorRepository;

    @Autowired
    public DirectorController(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    // GET all directors
    @GetMapping("/all")
    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }
}
