package com.ian.finance.domain.entity;

import com.ian.finance.client.dto.ClientDto;
import com.ian.finance.domain.dto.CompanyDto;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "COMPANY")
@Table(name = "COMPANYS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String ticker;

    private String name;

    public static Company from(CompanyDto company) {
        return Company.builder()
                .ticker(company.getTicker())
                .name(company.getName())
                .build();
    }
}
