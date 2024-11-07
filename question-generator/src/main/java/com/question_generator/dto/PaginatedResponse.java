package com.question_generator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginatedResponse<T> {

    private int pageSize;
    private int currPage;
    private int prevPage;
    private int nextPage;
    private int totalPages;
    private Long totalRecords;
    private boolean hasMore;
    private List<T> data;


}
