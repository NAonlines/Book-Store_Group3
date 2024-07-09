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
    private String name ;
    private String address;
    private String phone;
    private String email;
    
    public ModelPublisher(int publisher_id, String name, String address, String phone, String email) {
        this.publisher_id = publisher_id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public int getPublisher_id() {
        return publisher_id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}

