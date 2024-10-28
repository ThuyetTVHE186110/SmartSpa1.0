/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class AppointmentService {

    private int id;
    private int appointmentID;
    private Service service;
    private Person staff;

    public AppointmentService() {
    }

    public AppointmentService(int id, int appointmentID, Service service, Person staff) {
        this.id = id;
        this.appointmentID = appointmentID;
        this.service = service;
        this.staff = staff;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Person getStaff() {
        return staff;
    }

    public void setStaff(Person staff) {
        this.staff = staff;
    }

    
}
