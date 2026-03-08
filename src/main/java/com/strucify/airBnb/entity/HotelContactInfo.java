package com.strucify.airBnb.entity;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelContactInfo {
    private String address;
    private  String phoneNumber;
    private String email;
    private  String location;


}
