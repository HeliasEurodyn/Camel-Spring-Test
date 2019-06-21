package com.flexi.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class RouteStepGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

   /* @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "routesteps_in_routestepgrout",
            joinColumns = @JoinColumn(name = "routestepgroup_id"),
            inverseJoinColumns = @JoinColumn(name = "routestep_id")) */
   @OneToMany
    private List<RouteStep> routeSteps;

    private int shortOrder;


    public RouteStepGroup() {
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<RouteStep> getRouteSteps() {
        return routeSteps;
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

    public void setRouteSteps(List<RouteStep> routeSteps) {
        this.routeSteps = routeSteps;
    }

    public void setShortOrder(int shortOrder) {
        this.shortOrder = shortOrder;
    }
}
