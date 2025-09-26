package com.hawkscheck.hawkscheck.controller;

import com.hawkscheck.hawkscheck.dto.LoanRequestDTO;
import com.hawkscheck.hawkscheck.dto.LoanResponseDTO;
import com.hawkscheck.hawkscheck.service.EquipmentLoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class EquipmentLoanController {

    private final EquipmentLoanService loanService;

    @PostMapping("/borrow")
    public LoanResponseDTO borrow(@RequestBody LoanRequestDTO dto) {
        return loanService.borrowEquipment(dto);
    }

    @PutMapping("/return/{loanId}")
    public LoanResponseDTO returnEquipment(
            @PathVariable Long loanId,
            @RequestBody LoanRequestDTO dto
    ) {
        return loanService.returnEquipment(loanId, dto);
    }

    @GetMapping
    public List<LoanResponseDTO> listLoans() {
        return loanService.listLoans();
    }
}
