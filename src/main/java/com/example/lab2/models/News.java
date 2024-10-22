package com.example.lab2.models;


import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
