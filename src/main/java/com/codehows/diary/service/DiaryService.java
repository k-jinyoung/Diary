package com.codehows.diary.service;

import com.codehows.diary.domain.Diary;
import com.codehows.diary.dto.AddDiaryRequest;
import com.codehows.diary.dto.UpdateDiaryRequest;
import com.codehows.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;


    //등록 메소드
    public Diary save(AddDiaryRequest request, String userName){
        return diaryRepository.save(request.toEntity(userName));
    }

    //조회 메소드
    public List<Diary> findAll(){
        return diaryRepository.findAll();
    }

    public Diary findById(long id){
        return diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    //수정 메소드
    @Transactional
    public Diary update(long id, UpdateDiaryRequest request){
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        diary.update(request.getTitle(), request.getContent(), request.getStart());

        return diary;
    }

    //삭제 메소드
    public void delete(long id){
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }
}
