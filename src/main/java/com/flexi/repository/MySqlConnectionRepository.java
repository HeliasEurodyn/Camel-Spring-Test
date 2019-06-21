package com.flexi.repository;

import com.flexi.model.MySqlConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MySqlConnectionRepository extends JpaRepository<MySqlConnection, Long> {
}

