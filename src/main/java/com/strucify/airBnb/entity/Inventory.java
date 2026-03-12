package com.strucify.airBnb.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(
        uniqueConstraints =
        @UniqueConstraint(
                name = "unique_hotel_room_date",
                columnNames = {"hotel_id", "room_id", "date"}))
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    @ToString.Exclude
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    @ToString.Exclude
    private Room room;


    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer bookedCount;
    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer reservedCount;
    private Integer totalCount;
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal surgeFactor;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    private String city;

    @Column(nullable = false)
    @Builder.Default
    private Boolean closed = false;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
