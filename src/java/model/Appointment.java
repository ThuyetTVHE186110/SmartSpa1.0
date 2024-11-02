/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class Appointment {

    private int id;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createdDate;
    private String status;
    private String note;
    private Person customer;
    private List<AppointmentService> services;

    public Appointment() {
    }

    public Appointment(int id, LocalDateTime start, LocalDateTime end, LocalDateTime createdDate, String status, String note, Person customer, List<AppointmentService> services) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.createdDate = createdDate;
        this.status = status;
        this.note = note;
        this.customer = customer;
        this.services = services != null ? new ArrayList<>(services) : new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Person getCustomer() {
        return customer;
    }

    public void setCustomer(Person customer) {
        this.customer = customer;
    }

    public List<AppointmentService> getServices() {
        return services;
    }

    public void setServices(List<AppointmentService> services) {
        this.services = services != null ? new ArrayList<>(services) : new ArrayList<>();
    }

}
