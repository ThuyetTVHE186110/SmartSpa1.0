/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class Feedback {
    private int id;
    private String content;
    private Person customer;
    private Service service;
    private int StarRating;

    public Feedback() {
    }

    public Feedback(int id, String content, Person customer, Service service, int StarRating) {
        this.id = id;
        this.content = content;
        this.customer = customer;
        this.service = service;
        this.StarRating = StarRating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Person getCustomer() {
        return customer;
    }

    public void setCustomer(Person customer) {
        this.customer = customer;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public int getStarRating() {
        return StarRating;
    }

    public void setStarRating(int StarRating) {
        this.StarRating = StarRating;
    }

    

    public void add(Feedback feedback) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
