package com.example.lab2.models;


import lombok.*;
import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News {

    private Long id;
    private String title;
    private String content;
    private NewsCategory category;
    private LocalDate date;


}
