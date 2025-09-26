package com.hawkscheck.hawkscheck.repository;


import com.hawkscheck.hawkscheck.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
