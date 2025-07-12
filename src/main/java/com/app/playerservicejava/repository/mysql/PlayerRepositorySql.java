package com.app.playerservicejava.repository.mysql;
import com.app.playerservicejava.model.mysql.PlayerSql;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepositorySql extends JpaRepository<PlayerSql, String> {
}
