package com.question_generator.utils;

import com.question_generator.dto.CommonResponse;
import com.question_generator.dto.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ResponseUtil {

    public static <T> ResponseEntity<CommonResponse<T>> buildListErrorResponse(
            List<String> errorMessage, HttpStatus statusCode) {
        CommonResponse<T> res = new CommonResponse<>();
        res.setData(null);
        res.setSuccess(false);
        res.setError(errorMessage);
        res.setStatusCode(statusCode.value());
        res.setMessage("Internal Server Error");
        return new ResponseEntity<>(res, statusCode);
    }

    public static <T> ResponseEntity<CommonResponse<T>> buildErrorResponse(
            String errorMessage, HttpStatus statusCode) {
        CommonResponse<T> res = new CommonResponse<>();
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(errorMessage);
        res.setData(null);
        res.setSuccess(false);
        res.setError(errorMessages);
        res.setStatusCode(statusCode.value());
        res.setMessage("Internal Server Error");
        return new ResponseEntity<>(res, statusCode);
    }

    public static <T> ResponseEntity<CommonResponse<PaginatedResponse<T>>> buildPaginatedErrorResponse(
            String errorMessage, HttpStatus statusCode) {

        CommonResponse<PaginatedResponse<T>> res = new CommonResponse<>();
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(errorMessage);

        res.setData(null);
        res.setSuccess(false);
        res.setError(errorMessages);
        res.setStatusCode(statusCode.value());
        res.setMessage("Internal Server Error");

        return new ResponseEntity<>(res, statusCode);
    }

    public static <T> ResponseEntity<CommonResponse<PaginatedResponse<T>>> buildSuccessResponse(
            PaginatedResponse<T> data, String message) {

        CommonResponse<PaginatedResponse<T>> res = new CommonResponse<>();
        res.setData(data);
        res.setSuccess(true);
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage(message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    public static <T> ResponseEntity<CommonResponse<T>> buildCommonSuccessResponse(T data, String message) {
        CommonResponse<T> res = new CommonResponse<>();
        res.setData(data);
        res.setSuccess(true);
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage(message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    public static <T, K> PaginatedResponse<K> getPaginatedResponse(Page<T> data, Function<T, K> mapper) {
        List<K> mappedData = data.getContent().stream().map(mapper).toList();
        return PaginatedResponse.<K>builder()
                .data(mappedData)
                .currPage(data.getPageable().getPageNumber())
                .hasMore(data.hasNext())
                .nextPage(data.hasNext() ? data.getPageable().getPageNumber() + 1 : data.getPageable().getPageNumber())
                .prevPage(data.getPageable().getPageNumber() == 0 ? 0 : data.getPageable().getPageNumber() - 1)
                .pageSize(data.getSize())
                .totalPages(data.getTotalPages())
                .totalRecords(data.getTotalElements())
                .build();
    }

}
