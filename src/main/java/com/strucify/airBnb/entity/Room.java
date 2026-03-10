package com.strucify.airBnb.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @ToString.Exclude
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room", orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Inventory> inventories;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room", orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Booking> bookings;


    @Column(nullable = false)
    private String type;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(columnDefinition = "TEXT[]")
    private List<String> photos;

    @Column(columnDefinition = "TEXT[]")
    private List<String> amenities;

    @Column(nullable = false)
    private Integer totalCount;
    @Column(nullable = false)
    private Integer capacity;
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;


}
