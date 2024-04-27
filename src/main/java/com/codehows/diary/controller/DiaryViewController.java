package com.codehows.diary.controller;

import com.codehows.diary.domain.Diary;
import com.codehows.diary.dto.DiaryViewResponse;
import com.codehows.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@Controller
public class DiaryViewController {

    private final DiaryService diaryService;

    @GetMapping("/calendar")
    public String getCalendar(){

        return "calendar";
    }

    //새로 글 작성
    @GetMapping("/NewDiary")
    public String getNewDiary(@RequestParam(required = false) Long id, Model model){
        //id 키를 가진 쿼리 파라미터의 값을 id 변수에 매핑

        if (id == null){
            //새글 작성
            model.addAttribute("diary", new DiaryViewResponse());
        } else {
            Diary diary = diaryService.findById(id);
            model.addAttribute("diary", new DiaryViewResponse(diary));
        }
        return "addDiary";
    }

    /*@GetMapping("/UpdateDiary")
    public String getUpdateDiary(@RequestParam(required = false) Long id, Model model){
        if (id != null) {
            Diary diary = diaryService.findById(id);
            model.addAttribute("diary", new DiaryViewResponse(diary));
        }

        return "calendar";
    }*/

}
