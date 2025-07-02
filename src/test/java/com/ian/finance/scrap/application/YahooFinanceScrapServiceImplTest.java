package com.ian.finance.scrap.application;

import com.ian.finance.domain.dto.CompanyDto;
import com.ian.finance.domain.dto.CompanyInfoDto;
import com.ian.finance.domain.dto.DividendDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class YahooFinanceScrapServiceImplTest {

    private final ScrapService scraper = new YahooFinanceScrapServiceImpl();

    @Test
    void scrapCompanyByTickerTest() {

        // given
        String ticker = "COKE";
        String companyName = "Coca-Cola Consolidated, Inc.";

        // when
        CompanyDto company = scraper.scrapCompanyByTicker(ticker);

        // then
        assertThat(company.getTicker()).isEqualTo(ticker);
        assertThat(company.getName()).isEqualTo(companyName);
    }

    @Test
    void scrapDividendByCompanyTest() {

        // given
        CompanyDto company = CompanyDto.builder()
                .name("Apple Inc.")
                .ticker("AAPL")
                .build();

        // when
        CompanyInfoDto result = scraper.scrapDividendByCompany(company);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getCompany()).isEqualTo(company);

        List<DividendDto> dividends = result.getDividends();
        assertThat(dividends).isNotNull();
        assertThat(dividends.size()).isGreaterThan(0);

        DividendDto first = dividends.get(0);
        assertThat(first.getAmount()).isNotBlank();
        assertThat(first.getDate()).isNotNull();
    }
}