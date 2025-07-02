package com.ian.finance.client.dto;

import lombok.*;

public class ClientDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class CompanyNameResponse {
        private String name;

        public static CompanyNameResponse from(String name) {
            return CompanyNameResponse.builder()
                    .name(name)
                    .build();
        }
    }
}
