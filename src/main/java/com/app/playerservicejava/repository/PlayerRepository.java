package com.app.playerservicejava.repository;
import com.app.playerservicejava.model.Player;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, String> {

    List<Player> findAllPlayersByFirstName(String firstName, Pageable p);

    List<Player> findAllPlayersByFirstNameAndLastName(String firstName, String lastName);

    List<Player> findAllPlayersByWeightBetween(String minWeight, String maxWeight, Pageable pageable);

    @Query("select firstName, firstName from Player")
    List<Object[]> findAllPlayersPartialData();

    @Query("from Player")
    List<Player> findAllPlayers();

    @Query("from Player where CAST(weight AS int) >= :minWeight and  CAST(weight AS int) <= :maxWeight")
    List<Player> findPlayersWithinWeightRange(@Param("minWeight") int minWeight,
                                              @Param("maxWeight") int maxWeight);

    @Modifying
    @Transactional
    @Query("delete from Player where firstName like :firstName")
    void deleteByFirstName(@Param("firstName") String firstName);


//    @Query("SELECT p FROM Player p WHERE p.weight IS NOT NULL AND p.weight <> '' AND CAST(p.weight AS int) BETWEEN :minWeight AND :maxWeight")
//    List<Player> findAllPlayersByWeightBetween(@Param("minWeight") int minWeight, @Param("maxWeight") int maxWeight);

}
