package com.flexi.controller;

import com.flexi.model.SqlServerConnection;
import com.flexi.repository.SqlServerConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class SqlServerConnectionController {

    @Autowired
    private SqlServerConnectionRepository sqlServerConnectionRepository;

    @GetMapping("/sqlServerConnection/get")
    public List<SqlServerConnection> get(){
        return sqlServerConnectionRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
    }


    @GetMapping("/sqlServerConnection/getFirst")
    public SqlServerConnection getFirst(){
        List<SqlServerConnection> sqlServerConnections = sqlServerConnectionRepository.findAll();
        Optional<SqlServerConnection> sqlServerConnectionOption = sqlServerConnections.stream().findFirst();
        if(sqlServerConnectionOption.isPresent()) return  sqlServerConnectionOption.get();
        return null;
    }

    @DeleteMapping("/sqlServerConnection/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
         sqlServerConnectionRepository.deleteById(id);
         return ResponseEntity.noContent().build();
    }

    @PostMapping("/sqlServerConnection/update")
    public SqlServerConnection update(@RequestBody SqlServerConnection sqlServerConnection){
        return sqlServerConnectionRepository.save(sqlServerConnection);
    }


}
