package com.authentication.Authentication.cammonDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginatedReq {
    private int page;
    private int limit;
    private Boolean isActive;
}
