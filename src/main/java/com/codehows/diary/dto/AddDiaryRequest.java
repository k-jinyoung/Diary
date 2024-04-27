package com.codehows.diary.dto;

import com.codehows.diary.domain.Diary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddDiaryRequest {

    private String title;
    private String content;
    private String start;
    private String author;

    public Diary toEntity(String author){
        return Diary.builder()
                .title(title)
                .content(content)
                .start(start)
                .author(author)
                .build();
    }
}
