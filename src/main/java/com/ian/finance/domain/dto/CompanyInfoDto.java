package com.ian.finance.domain.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CompanyInfoDto {
    private CompanyDto company;
    private List<DividendDto> dividends;
}
