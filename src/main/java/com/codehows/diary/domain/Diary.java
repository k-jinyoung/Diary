package com.codehows.diary.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Diary {

    //데이터베이스에 필요한 필수 것들
    @Id     //id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //기본키를 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)       //nullable = false 는 not null 이라는 뜻
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "start_at")
    private String start;

    @Column(name = "author", nullable = false)
    private String author;

    @Builder
    public Diary(String title, String content, String start, String author){
        this.title = title;
        this.content = content;
        this.start = start;
        this.author = author;
    }

    //수정하는 메소드
    public void update(String title, String content, String start){
        this.title = title;
        this.content = content;
        this.start = start;
    }
}
