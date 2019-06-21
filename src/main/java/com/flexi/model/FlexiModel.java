package com.flexi.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class FlexiModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany
    private List<RouteStepGroup> routeStepGroups;

    private int shortOrder;


    public FlexiModel() {
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<RouteStepGroup> getRouteStepGroups() {
        return routeStepGroups;
    }

    public int getShortOrder() {
        return shortOrder;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRouteStepGroups(List<RouteStepGroup> routeStepGroups) {
        this.routeStepGroups = routeStepGroups;
    }

    public void setShortOrder(int shortOrder) {
        this.shortOrder = shortOrder;
    }


}
