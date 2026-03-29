package com.library.dea.dto;

import com.library.dea.validation.ValidPrice;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BookDTO {

    private Integer id;

    @NotBlank(message = "{error.notblank}")
    @Size(min = 2, max = 100, message = "{error.size}")
    private String title;

    @NotNull(message = "{book.validation.price.required}")
    @ValidPrice
    private Double price;

    @NotNull(message = "{book.validation.amount.required}")
    @Min(value = 1, message = "{book.validation.amount.min}")
    @Max(value = 1000, message = "{book.validation.amount.max}")
    private Integer amount;

    private Long authorId;

    private String authorName;

    public BookDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
