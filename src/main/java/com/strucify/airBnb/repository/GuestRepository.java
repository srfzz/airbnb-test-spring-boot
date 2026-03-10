package com.strucify.airBnb.repository;

import com.strucify.airBnb.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
}