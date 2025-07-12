package com.app.playerservicejava.service.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataImporter implements CommandLineRunner {

    @Autowired
    private PlayerServiceSql playerService;

    @Override
    public void run(String... args) {
        String filePath = "src/main/resources/Player.csv"; // Update the path if needed
        playerService.importPlayersFromCsv(filePath);
        System.out.println("Players imported successfully.");
    }
}