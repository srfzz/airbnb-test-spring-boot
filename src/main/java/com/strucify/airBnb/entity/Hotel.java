package com.strucify.airBnb.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    private String city;
    @Column(columnDefinition = "TEXT[]")
    private List<String> photos;
    @Column(columnDefinition = "TEXT[]")
    private List<String> amenities;



    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Room> rooms;
    @CreatedDate


    @OneToMany(mappedBy = "hotel", cascade =  CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Inventory> inventories;

    @OneToMany(mappedBy = "hotel", cascade =  CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Booking> bookings ;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Embedded
    private HotelContactInfo contactInfo;

    @Column(nullable = false)
    private Boolean active;


}
