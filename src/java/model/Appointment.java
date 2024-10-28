/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class Appointment {

    private int id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private LocalDateTime createdDate;
    private String status;
    private String note;
    private Person customer;
    private List<AppointmentService> services;

    public Appointment() {
    }

    public Appointment(int id, LocalDate appointmentDate, LocalTime appointmentTime, LocalDateTime createdDate, String status, String note, Person customer, List<AppointmentService> services) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
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

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
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
