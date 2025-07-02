package com.ian.finance.domain.dto;

import com.ian.finance.domain.entity.Company;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CompanyDto {

    private String ticker;
    private String name;

    public static CompanyDto from(Company company) {
        return CompanyDto.builder()
                .ticker(company.getTicker())
                .name(company.getName())
                .build();
    }
}
