package com.flexi.model;

import org.apache.camel.Processor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
public class RouteStep implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    private RouteStepTypes routeStepType;
    private String  routeAction = "";

    @ElementCollection
    private Map<String,String> options = new HashMap<String,String>();

    @Transient
    private Processor processor = null;

    private int shortOrder;



    public RouteStep(RouteStepTypes routeStepType) {
        this.routeStepType = routeStepType;
    }

    public RouteStep(RouteStepTypes routeStepType, String routeAction) {
        this.routeStepType = routeStepType;
        this.routeAction = routeAction;
    }

    public RouteStep(RouteStepTypes routeStepType, Processor processor) {
        this.routeStepType = routeStepType;
        this.processor = processor;
    }



    public int getId() {
        return id;
    }

    public RouteStepTypes getRouteStepType() {
        return routeStepType;
    }

    public String getRouteAction() {
        return routeAction;
    }

    public Processor getProcessor(){
        return this.processor;
    }

    public Map getOptions() {
        return options;
    }

    public Object getOption(String key) {
        return options.get(key);
    }

    public int getShortOrder() {
        return shortOrder;
    }



    public void setRouteAction(String routeAction) {
        this.routeAction = routeAction;
    }

    public void setShortOrder(int shortOrder) {
        this.shortOrder = shortOrder;
    }

    public void setRouteStepType(RouteStepTypes routeStepType) {
        this.routeStepType = routeStepType;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public void setOptions(Map routeActionOptions) {
        this.options = routeActionOptions;
    }

    public void setOption(String key, Object Value) {
        this.options.put(key,Value.toString());
    }

}





