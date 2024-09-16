package com.cricketService.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeriesWithDateDto {
    private Long id;
    private String date;
    private List<SeriesDto> series;
}
