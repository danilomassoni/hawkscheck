package com.hawkscheck.hawkscheck.service;

import com.hawkscheck.hawkscheck.dto.LoanRequestDTO;
import com.hawkscheck.hawkscheck.dto.LoanResponseDTO;
import com.hawkscheck.hawkscheck.model.*;
import com.hawkscheck.hawkscheck.repository.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentLoanService {

    private final EquipmentLoanRepository loanRepository;
    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;

    public LoanResponseDTO borrowEquipment(LoanRequestDTO dto) {
        Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        User professor = userRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        EquipmentLoan loan = new EquipmentLoan();
        loan.setEquipment(equipment);
        loan.setProfessor(professor);
        loan.setBorrowedAt(LocalDateTime.now());
        loan.setSignatureCollected(dto.isSignatureCollected());
        loan.setNotes(dto.getNotes());

        loan = loanRepository.save(loan);

        return toDTO(loan);
    }

    public LoanResponseDTO returnEquipment(Long loanId, LoanRequestDTO dto) {
        EquipmentLoan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        loan.setReturnedAt(LocalDateTime.now());
        loan.setConditionOnReturn(dto.getConditionOnReturn());
        loan.setNotes(dto.getNotes());

        loan = loanRepository.save(loan);

        return toDTO(loan);
    }

    public List<LoanResponseDTO> listLoans() {
        return loanRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private LoanResponseDTO toDTO(EquipmentLoan loan) {
        LoanResponseDTO dto = new LoanResponseDTO();
        dto.setId(loan.getId());
        dto.setEquipmentId(loan.getEquipment().getId());
        dto.setEquipmentName(loan.getEquipment().getBrandModel());
        dto.setProfessorId(loan.getProfessor().getId());
        dto.setProfessorName(loan.getProfessor().getName());
        dto.setBorrowedAt(loan.getBorrowedAt());
        dto.setReturnedAt(loan.getReturnedAt());
        dto.setSignatureCollected(loan.isSignatureCollected());
        dto.setNotes(loan.getNotes());
        dto.setConditionOnReturn(loan.getConditionOnReturn());
        return dto;
    }
}
