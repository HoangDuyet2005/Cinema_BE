package com.example.goldenticketnew.payload.response;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageResponse<T> {
    private List<T> content = new ArrayList<>();
    private int number;
    private int size;
    private Long totalElements;
    private int totalPages;

    public PageResponse() {

    }

    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.number = page.getNumber();
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }

    public PageResponse(List<T> content, int number, int size, long totalElements, int totalPages) {
        this.content = content;
        this.number = number;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public PageResponse(List<T> content, Page<?> page) {
        this.content = content;
        this.number = page.getNumber();
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }
}
