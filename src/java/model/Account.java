/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author PC
 */
public class Account {

    private int id;
    private String username;
    private String password;
    private int role;  // Role is now an int
    private Person personInfo;
    private String status; 
    private String roleName; // Tên vai trò (Role Name)

    public Account() {
    }

    public Account(int id, String username, String password, int role, Person personInfo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.personInfo = personInfo;

    }
    public Account(int id, String username, String password, int role, Person personInfo, String status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.personInfo = personInfo;
        this.status = status; // Thêm status trong constructor
    }

    // Getters and setters for roleName
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;  // Change role getter and setter to work with int
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Person getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(Person personInfo) {
        this.personInfo = personInfo;
    }
}
