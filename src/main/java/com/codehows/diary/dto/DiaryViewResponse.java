package com.codehows.diary.dto;

import com.codehows.diary.domain.Diary;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class DiaryViewResponse {

    private Long id;
    private String title;
    private String content;
    private String start;
    private String author;

    public DiaryViewResponse(Diary diary){
        this.id = diary.getId();
        this.title = diary.getTitle();
        this.content = diary.getContent();
        this.start = diary.getStart();
        this.author = diary.getAuthor();
    }

}
