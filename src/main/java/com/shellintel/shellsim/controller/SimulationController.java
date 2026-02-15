package com.shellintel.shellsim.controller;

import com.shellintel.shellsim.service.SimulationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/simulation")
public class SimulationController {

    private final SimulationService simulationService;

    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @PostMapping("/run")
    public String runSimulation() {
        simulationService.runSimulation();
        return "redirect:/dashboard";
    }
}
