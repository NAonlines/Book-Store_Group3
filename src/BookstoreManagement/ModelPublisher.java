/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BookstoreManagement;

/**
 *
 * @author ADMIN
 */
public class ModelPublisher {
    private int publisher_id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String title;

    public ModelPublisher(int publisher_id, String name, String address, String phone, String email, String title) {
        this.publisher_id = publisher_id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.title = title;
    }

    // Getters and setters
    public int getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(int publisher_id) {
        this.publisher_id = publisher_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}


