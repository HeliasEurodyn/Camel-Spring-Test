package com.flexi.controller;

import com.flexi.model.Rest;
import com.flexi.repository.RestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private RestRepository restRepository;

    @GetMapping("/rest/get")
    public List<Rest> get(){
        return restRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
    }


    @GetMapping("/rest/getFirst")
    public Rest getFirst(){
        List<Rest> sqlServerConnections = restRepository.findAll();
        Optional<Rest> sqlServerConnectionOption = sqlServerConnections.stream().findFirst();
        if(sqlServerConnectionOption.isPresent()) return  sqlServerConnectionOption.get();
        return null;
    }

    @DeleteMapping("/rest/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        restRepository.deleteById(id);
         return ResponseEntity.noContent().build();
    }

    @PostMapping("/rest/update")
    public Rest update(@RequestBody Rest sqlServerConnection){
        return restRepository.save(sqlServerConnection);
    }


}
