package com.codehows.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateDiaryRequest {

    private String title;
    private String content;
    private String start;
}
