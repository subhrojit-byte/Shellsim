package com.shellintel.shellsim;

import com.shellintel.shellsim.service.SimulationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ShellsimApplication {

	public static void main(String[] args) {

		ApplicationContext context= SpringApplication.run(ShellsimApplication.class, args);
		SimulationService simulationService= context.getBean(SimulationService.class);
	}

}
