package com.flexi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Rest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;
    private int port;
    private Boolean enableCors;



    public Rest() {
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getPort() {
        return port;
    }
    public Boolean getEnableCors() {
        return enableCors;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void setPort(int port) {
        this.port = port;
    }

    public void setEnableCors(Boolean enableCors) {
        this.enableCors = enableCors;
    }
}
