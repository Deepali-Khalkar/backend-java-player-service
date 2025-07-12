package com.app.playerservicejava.model.mysql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PLAYERS")
@Getter
@Setter
public class PlayerSql {
    @Id
    private String playerID;
    private Integer birthYear;
    private Integer birthMonth;
    private Integer birthDay;
    private String birthCountry;
    private String birthState;
    private String birthCity;
    private Integer deathYear;
    private Integer deathMonth;
    private Integer deathDay;
    private String deathCountry;
    private String deathState;
    private String deathCity;
    private String nameFirst;
    private String nameLast;
    private String nameGiven;
    private Integer weight;
    private Integer height;
    private String bats;
    private String throwsField; // Note: "throws" is a reserved keyword, so renamed here
    private java.sql.Date debut;
    private java.sql.Date finalGame;
    private String retroID;
    private String bbrefID;
}