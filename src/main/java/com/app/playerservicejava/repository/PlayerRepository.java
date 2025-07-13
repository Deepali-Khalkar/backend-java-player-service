package com.app.playerservicejava.repository;
import com.app.playerservicejava.model.Player;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PlayerRepository extends JpaRepository<Player, String> {

    List<Player> findAllPlayersByFirstName(String firstName);

    List<Player> findAllPlayersByLastName(String lastName);

    List<Player> findAllPlayersBygivenName(String givenName);

    List<Player> findAllPlayersByWeightBetween(String minWeight, String maxWeight);

//    List<Player> findAll(Pageable pageable);

    @Transactional
    void deleteAllPlayersByFirstName(String firstName);

    @Query("select count(*) from Player where firstName like :firstName")
    int countFirstName(@Param("firstName") String firstName);

}
