package com.ian.finance.domain.dto;

import com.ian.finance.domain.entity.Dividend;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DividendDto {
    private LocalDate date;
    private String amount;

    public static DividendDto from(Dividend dividend) {
        return DividendDto.builder()
                .date(dividend.getDate())
                .amount(dividend.getAmount())
                .build();
    }
}
