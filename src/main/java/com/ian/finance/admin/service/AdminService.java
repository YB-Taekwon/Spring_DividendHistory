package com.ian.finance.admin.service;

import com.ian.finance.domain.dto.CompanyDto;
import com.ian.finance.domain.dto.CompanyInfoDto;
import com.ian.finance.domain.entity.Company;
import com.ian.finance.domain.repository.CompanyRepository;
import com.ian.finance.domain.entity.Dividend;
import com.ian.finance.domain.repository.DividendRepository;
import com.ian.finance.scrap.application.ScrapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final ScrapService yahooFinanceScrapService;
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public CompanyInfoDto registerCompanyByTicker(String ticker) {

        // ticker가 없으면 company 정보 스크랩, 이미 존재한다면 예외 발생
        if (companyRepository.existsByTicker(ticker)) {
            log.error("이미 저장된 ticker 입니다.");
            throw new RuntimeException();
        }

        return scrapAndSaveCompanyInfo(ticker);
    }

    private CompanyInfoDto scrapAndSaveCompanyInfo(String ticker) {

        // 1. ticker로 회사 정보를 스크래핑
        CompanyDto companyDto = yahooFinanceScrapService.scrapCompanyByTicker(ticker);

        // 2. 회사가 존재하면 해당 회사의 배당금 정보를 스크래핑
        CompanyInfoDto companyInfo = yahooFinanceScrapService.scrapDividendByCompany(companyDto);

        // 3. 스크래핑 결과 저장
        Company company = companyRepository.save(Company.from(companyDto));

        List<Dividend> dividends = companyInfo.getDividends().stream()
                .map(dividend -> Dividend.from(company, dividend))
                .toList();
        dividendRepository.saveAll(dividends);

        return companyInfo;
    }
}
