package com.flexi.controller;

import com.flexi.model.MySqlConnection;
import com.flexi.repository.MySqlConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class MySqlConnectionController {

    @Autowired
    private MySqlConnectionRepository mysqlConnectionRepository;

    @GetMapping("/mySqlConnection/get")
    public List<MySqlConnection> get(){
        return mysqlConnectionRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
    }


    @GetMapping("/mySqlConnection/getFirst")
    public MySqlConnection getFirst(){
        List<MySqlConnection> sqlServerConnections = mysqlConnectionRepository.findAll();
        Optional<MySqlConnection> sqlServerConnectionOption = sqlServerConnections.stream().findFirst();
        if(sqlServerConnectionOption.isPresent()) return  sqlServerConnectionOption.get();
        return null;
    }

    @DeleteMapping("/mySqlConnection/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        mysqlConnectionRepository.deleteById(id);
         return ResponseEntity.noContent().build();
    }

    @PostMapping("/mySqlConnection/update")
    public MySqlConnection update(@RequestBody MySqlConnection sqlServerConnection){
        return mysqlConnectionRepository.save(sqlServerConnection);
    }


}
