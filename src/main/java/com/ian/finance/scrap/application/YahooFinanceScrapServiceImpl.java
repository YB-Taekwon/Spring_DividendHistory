package com.ian.finance.scrap.application;

import com.ian.finance.domain.dto.CompanyDto;
import com.ian.finance.domain.dto.DividendDto;
import com.ian.finance.domain.dto.CompanyInfoDto;
import com.ian.finance.scrap.domain.Month;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class YahooFinanceScrapServiceImpl implements ScrapService {

    private static final String STATISTICS_URL = "https://finance.yahoo.com/quote/%s/history/?frequency=1mo&period1=%d&period2=%d&filter=div";
    private static final String SUMMARY_URL = "https://finance.yahoo.com/quote/%s/";
    private static final long START_TIME = 60 * 60 * 24;

    // ticker로 회사명 스크랩
    @Override
    public CompanyDto scrapCompanyByTicker(String ticker) {
        log.info("회사 정보 스크랩 시작");
        log.debug("ticker={}", ticker);

        try {
            String url = String.format(SUMMARY_URL, ticker);
            Document parshingDiv = parse(url);

            Element h1 = parshingDiv.selectFirst("h1.yf-4vbjci");
            if (h1 == null) {
                log.error("h1 태그를 찾을 수 없습니다.");
                throw new RuntimeException();
            }

            String companyName = h1.text().replaceAll("\\s*\\(.*?\\)\\s*", "").trim();

            log.debug("companyName={}", companyName);
            log.info("스크랩 종료");

            return CompanyDto.builder()
                    .name(companyName)
                    .ticker(ticker)
                    .build();

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("회사 정보를 스크랩할 수 없습니다.");
        }
    }

    // ticker에 해당하는 회사의 전체 기간 배당금 데이터 스크랩
    @Override
    public CompanyInfoDto scrapDividendByCompany(CompanyDto company) {

        log.info("배당금 정보 스크랩 시작");
        log.debug("name={}, ticker={}", company.getName(), company.getTicker());

        try {
            long now = System.currentTimeMillis() / 1000;
            String url = String.format(STATISTICS_URL, company.getTicker(), START_TIME, now);

            Document parshingDiv = parse(url);

            Element table = parshingDiv.selectFirst("table.noDl");
            Element tbody = table.child(1);

            List<DividendDto> dividends = new ArrayList<>();

            for (Element e : tbody.children()) {
                String[] split = e.text().split(" ");
                int month = Month.strToNum(split[0]);
                int day = Integer.valueOf(split[1].replace(",", ""));
                int year = Integer.valueOf(split[2]);
                String amount = split[3];

                if (month < 0) {
                    log.error("잘못된 month: " + split[0]);
                    throw new RuntimeException();
                }

                DividendDto dividendDto = DividendDto.builder()
                        .date(LocalDate.of(year, month, day))
                        .amount(amount)
                        .build();

                dividends.add(dividendDto);
            }

            log.info("스크랩 종료");
            return CompanyInfoDto.builder()
                    .company(company)
                    .dividends(dividends)
                    .build();

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("배당금 정보를 스크랩할 수 없습니다.");
        }
    }

    private Document parse(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("accept-encoding", "gzip, deflate, br")
                .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,es;q=0.6")
                .referrer("https://finance.yahoo.com")
                .get();
    }

}
