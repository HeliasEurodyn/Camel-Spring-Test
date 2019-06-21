package com.flexi.repository;

import com.flexi.model.SqlServerConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SqlServerConnectionRepository extends JpaRepository<SqlServerConnection, Long> {
}

