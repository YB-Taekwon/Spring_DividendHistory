package com.ian.finance.scrap.application;

import com.ian.finance.domain.dto.CompanyDto;
import com.ian.finance.domain.dto.CompanyInfoDto;

public interface ScrapService {
    CompanyDto scrapCompanyByTicker(String ticker);

    CompanyInfoDto scrapDividendByCompany(CompanyDto company);
}
