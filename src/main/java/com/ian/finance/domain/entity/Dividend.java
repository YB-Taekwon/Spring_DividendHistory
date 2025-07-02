package com.ian.finance.domain.entity;

import com.ian.finance.domain.dto.DividendDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "DIVIDEND")
@Table(name = "DIVIDENDS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Dividend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;

    private LocalDate date;

    private String amount;

    public static Dividend from(Company company, DividendDto dividend) {
        return Dividend.builder()
                .companyId(company.getId())
                .date(dividend.getDate())
                .amount(dividend.getAmount())
                .build();
    }
}
