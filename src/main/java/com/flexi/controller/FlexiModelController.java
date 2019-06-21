package com.flexi.controller;

import com.flexi.model.FlexiModel;
import com.flexi.repository.FlexiModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlexiModelController {

    @Autowired
    private FlexiModelRepository flexiModelRepository;

    @GetMapping("/flexiModel/get")
    public List<FlexiModel> get(){
        return flexiModelRepository.findAll();
    }

}
