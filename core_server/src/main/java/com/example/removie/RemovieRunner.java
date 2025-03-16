package com.example.removie;

import com.example.removie.update.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RemovieRunner implements CommandLineRunner {
    private final ApplicationContext context;
    private final UpdateService updateService;

    @Autowired
    public RemovieRunner(ApplicationContext context, UpdateService updateService) {
        this.context = context;
        this.updateService = updateService;
    }

    @Override
    public void run(String... args) {
        updateService.runUpdate();
        SpringApplication.exit(context);
    }
}
