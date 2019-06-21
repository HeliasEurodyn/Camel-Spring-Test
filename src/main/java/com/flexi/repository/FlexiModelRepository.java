package com.flexi.repository;

import com.flexi.model.FlexiModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlexiModelRepository extends JpaRepository<FlexiModel, Long> {
}

