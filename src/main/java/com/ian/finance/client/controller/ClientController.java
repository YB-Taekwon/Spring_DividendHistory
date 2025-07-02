package com.ian.finance.client.controller;

import com.ian.finance.client.service.ClientService;
import com.ian.finance.client.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/finance")
public class ClientController {

    private final ClientService clientService;

    // 모든 회사 목록 조회 API, 사용자 서비스
    @GetMapping
    public ResponseEntity<?> getCompanyList(final Pageable pageable) {
        Page<ClientDto.CompanyNameResponse> companyList = clientService.getCompanyList(pageable);
        return ResponseEntity.ok(companyList);
    }
}
