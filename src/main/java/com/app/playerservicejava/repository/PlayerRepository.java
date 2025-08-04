package com.app.playerservicejava.repository;
import com.app.playerservicejava.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, String> {

    List<Player> findByFirstName(String firstName);

    List<Player> findByFirstNameAndLastName(String firstName, String lastName);

    List<Player> findByFirstNameOrLastName(String firstName, String lastName);

    Page<Player> findAll(Pageable pageable);

    Player save(Player player);

    @Modifying
    @Transactional
    @Query("update Player set firstName=:firstName, lastName=:lastName where playerId=:id")
    void updateFirstAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("id") String id);


}
