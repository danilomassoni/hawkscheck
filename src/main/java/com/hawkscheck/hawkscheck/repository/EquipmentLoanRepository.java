package com.hawkscheck.hawkscheck.repository;

import com.hawkscheck.hawkscheck.model.EquipmentLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentLoanRepository extends JpaRepository<EquipmentLoan, Long> {

    
    List<EquipmentLoan> findByEquipmentId(Long equipmentId);
    List<EquipmentLoan> findByProfessorId(Long professorId);
    List<EquipmentLoan> findByReturnedAtIsNull();
}
