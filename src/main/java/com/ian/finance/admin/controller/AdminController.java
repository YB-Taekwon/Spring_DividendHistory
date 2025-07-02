package com.ian.finance.admin.controller;

import com.ian.finance.admin.dto.AdminDto;
import com.ian.finance.admin.service.AdminService;
import com.ian.finance.domain.dto.CompanyInfoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/finance/company")
public class AdminController {

    private final AdminService adminService;

    // 회사 및 배당금 정보 저장 API, 관리자 기능
    @PostMapping
    public ResponseEntity<?> registerCompanyByTicker(@Valid @RequestBody AdminDto request) {
        CompanyInfoDto company = adminService.registerCompanyByTicker(request.getTicker());
        return ResponseEntity.ok(company);
    }
}
