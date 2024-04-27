package com.codehows.diary.controller;

import com.codehows.diary.domain.Diary;
import com.codehows.diary.dto.AddDiaryRequest;
import com.codehows.diary.dto.DiaryViewResponse;
import com.codehows.diary.dto.UpdateDiaryRequest;
import com.codehows.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController     //HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class DiaryApiController {

    private final DiaryService diaryService;

    @GetMapping("/api/diaries")
    public ResponseEntity<List<DiaryViewResponse>> findAllDiaries(){
        List<DiaryViewResponse> diaries = diaryService.findAll()
                .stream()
                .map(DiaryViewResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(diaries);
    }

    //등록
    @PostMapping("/api/diaries")
    public ResponseEntity<Diary> addDiary(@RequestBody AddDiaryRequest request, Principal principal){
        Diary savedDiary = diaryService.save(request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedDiary);
    }


    //조회
    @GetMapping("/api/diaries/{id}")
    //URL 경로에서 값 추출
    public ResponseEntity<DiaryViewResponse> findDiary(@PathVariable long id){
        Diary diary = diaryService.findById(id);

        return ResponseEntity.ok()
                .body(new DiaryViewResponse(diary));
    }

    //수정(스프링부트 CRUD에서의 수정은 Put)
    @PutMapping("/api/diaries/{id}")
    public ResponseEntity<Diary> updateDiary(@PathVariable long id, @RequestBody UpdateDiaryRequest request){
        Diary updateDiary = diaryService.update(id, request);

        return ResponseEntity.ok()
                .body(updateDiary);
    }

    //삭제
    @DeleteMapping("/api/diaries/{id}")
    public ResponseEntity<Void> deleteDiary(@PathVariable long id){
        diaryService.delete(id);

        return ResponseEntity.ok()
                .build();
    }
}
