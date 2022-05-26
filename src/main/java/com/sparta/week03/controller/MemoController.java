package com.sparta.week03.controller;

import com.sparta.week03.domain.Memo;
import com.sparta.week03.domain.MemoRepository;
import com.sparta.week03.domain.MemoRequestDto;
import com.sparta.week03.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
public class MemoController{
    private final MemoRepository memoRepository;
    private final MemoService memoService;

    //게시판 글생성
    @PostMapping("/api/memos")
    public Memo createMemo(@RequestBody MemoRequestDto requestDto){
        Memo memo = new Memo(requestDto);
        return memoRepository.save(memo);
    }


    //게시판 조회
//    @GetMapping("/api/memos")
//    public List<Memo> readMemo(){
//        LocalDateTime start=LocalDateTime.now().minusDays(1);
//        LocalDateTime end=LocalDateTime.now();
//        return memoRepository.findAllByModifiedAtBetweenOrderByModifiedAtDesc(start,end);
//    }
    //게시판 조회
    @GetMapping("/api/memos")
    public List<Memo> readMemo(){
//        return memoRepository.findAllById(id,Sort sort);
        List<Memo> memos = memoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return memos;
    }

    //특정 메모 조회
    @GetMapping("/api/memos/{id}")
    public Memo readMemo(@PathVariable Long id){
        Memo memo=memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return memo;
    }

    // 특정 메모 수정
    @PutMapping("/api/memos/{id}")
    public String updateMemo(@PathVariable Long id, @RequestBody  MemoRequestDto requestDto){
//        memoService.update(id,requestDto);
        Optional<Memo> memo=memoRepository.findById(id);
        if(memo.get().getPw().equals(requestDto.getPw())){
            memoService.update(id,requestDto);
            return "정상적으로 수정되었습니다!";
        }
        return "비밀번호가 올바르지 않습니다.";
    }

    //특정 메모 삭제
    @DeleteMapping("/api/memos/{id}")
    public String deleteMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto){
        Optional<Memo> memo=memoRepository.findById(id);
        if(memo.get().getPw().equals(requestDto.getPw())){
            memoRepository.deleteById(id);
            return "정상적으로 삭제되었습니다!";
        }
        return "비밀번호가 올바르지 않습니다.";
    }

}
