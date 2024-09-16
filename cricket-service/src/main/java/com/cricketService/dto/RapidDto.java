package com.cricketService.dto;

import com.cricketService.entities.TeamsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RapidDto {
    private List<TeamsEntity> list;
}
