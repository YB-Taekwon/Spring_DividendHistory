package com.ian.finance.client.service;

import com.ian.finance.client.dto.ClientDto;
import com.ian.finance.domain.repository.CompanyRepository;
import com.ian.finance.domain.repository.DividendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public Page<ClientDto.CompanyNameResponse> getCompanyList(Pageable pageable) {
        Page<String> companies = companyRepository.findAllCompanyName(pageable);
        return companies.map(ClientDto.CompanyNameResponse::from);
    }
}
