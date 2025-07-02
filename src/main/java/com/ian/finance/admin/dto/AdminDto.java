package com.ian.finance.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminDto {

    @NotBlank
    private String ticker;
}
