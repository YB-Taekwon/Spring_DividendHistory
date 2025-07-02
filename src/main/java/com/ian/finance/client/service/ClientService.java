package com.ian.finance.client.service;

import com.ian.finance.client.dto.ClientDto;
import com.ian.finance.domain.dto.CompanyInfoDto;
import com.ian.finance.domain.entity.Company;
import com.ian.finance.domain.dto.CompanyDto;
import com.ian.finance.domain.repository.CompanyRepository;
import com.ian.finance.domain.entity.Dividend;
import com.ian.finance.domain.dto.DividendDto;
import com.ian.finance.domain.repository.DividendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public CompanyInfoDto getDividendsByTicker(String ticker) {

        // 1. 티커로 회사 정보 조회
        Company company = companyRepository.findByTicker(ticker)
                .orElseThrow(() -> {
                    log.error("일치하는 회사를 찾을 수 없습니다. ticker={}", ticker);
                    return new RuntimeException();
                });

        // 2. 조회된 회사 ID로 배당금 정보 조회
        List<Dividend> dividends = dividendRepository.findAllByCompanyId(company.getId());

        // 3. 결과 조합 후 응답 반환
        return CompanyInfoDto.builder()
                .company(CompanyDto.from(company))
                .dividends(dividends.stream().map(DividendDto::from).toList())
                .build();
    }
}
