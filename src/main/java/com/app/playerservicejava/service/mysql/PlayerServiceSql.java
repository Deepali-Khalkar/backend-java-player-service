package com.app.playerservicejava.service.mysql;

import com.app.playerservicejava.model.mysql.PlayerSql;
import com.app.playerservicejava.repository.mysql.PlayerRepositorySql;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerServiceSql {

    @Autowired
    private PlayerRepositorySql playerRepository;

    public void importPlayersFromCsv(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            List<PlayerSql> players = new ArrayList<>();

            // Skip the header
            reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                PlayerSql player = new PlayerSql();
                player.setPlayerID(nextLine[0]);
                player.setBirthYear(parseInt(nextLine[1]));
                player.setBirthMonth(parseInt(nextLine[2]));
                player.setBirthDay(parseInt(nextLine[3]));
                player.setBirthCountry(nextLine[4]);
                player.setBirthState(nextLine[5]);
                player.setBirthCity(nextLine[6]);
                player.setDeathYear(parseInt(nextLine[7]));
                player.setDeathMonth(parseInt(nextLine[8]));
                player.setDeathDay(parseInt(nextLine[9]));
                player.setDeathCountry(nextLine[10]);
                player.setDeathState(nextLine[11]);
                player.setDeathCity(nextLine[12]);
                player.setNameFirst(nextLine[13]);
                player.setNameLast(nextLine[14]);
                player.setNameGiven(nextLine[15]);
                player.setWeight(parseInt(nextLine[16]));
                player.setHeight(parseInt(nextLine[17]));
                player.setBats(nextLine[18]);
                player.setThrowsField(nextLine[19]);
                player.setDebut(java.sql.Date.valueOf(nextLine[20]));
                player.setFinalGame(java.sql.Date.valueOf(nextLine[21]));
                player.setRetroID(nextLine[22]);
                player.setBbrefID(nextLine[23]);
                players.add(player);
            }

            // Save all players in the database
            playerRepository.saveAll(players);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Integer parseInt(String value) {
        try {
            return value != null && !value.isEmpty() ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}