package io.github.chw3021.bookmakase.goal.service;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.goal.repository.LikedBookRepository;
import io.github.chw3021.bookmakase.interparkapi.client.InterparkClient;
import io.github.chw3021.bookmakase.signservice.controller.SignException;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import io.github.chw3021.bookmakase.signservice.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@RequiredArgsConstructor
public class BookRecommendService {
    @Autowired
    private final BookShelfService bookShelfService;
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final LikedBookRepository likedBookRepository;
    @Autowired
    private final InterparkClient interparkClient;

    private List<Book> similarAge(Member member){

        List<Book> similarAgeMemberLiked = new ArrayList<>();
        memberRepository.findAllByAgeBetween(member.getAge()-5, member.getAge()+5).forEach(m ->{
            if(m == member){
                return;
            }
            bookShelfService.getLikedBooksByMemberId(m.getId()).forEach(lb ->{
                similarAgeMemberLiked.add(lb.getBook());
            });
        });
        return similarAgeMemberLiked;
    }
    private List<Book> similar(Member member){

        List<Book> similarMemberLiked = new ArrayList<>();
        HashSet<Member> similar = new HashSet<>();
        bookShelfService.getLikedBooksByMemberId(member.getId()).forEach(lb ->{
            likedBookRepository.findAllByBook(lb.getBook()).forEach(flb -> similar.add(flb.getMember()));
        });
        similar.forEach(sm -> bookShelfService.getLikedBooksByMemberId(sm.getId()).forEach(lb -> {
            similarMemberLiked.add(lb.getBook());
        }));
        return similarMemberLiked;
    }
    private HashSet<Book> randomGet(Integer categoryId, Integer count){
        HashSet<Book> recommends = new HashSet<>();
        List<String> name = Arrays.asList("김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "한", "오", "서", "신", "권", "황", "안",
                "송", "류", "전", "홍", "고", "문", "양", "손", "배", "조", "백", "허", "유", "남", "심", "노", "정", "하", "곽", "성", "차", "주",
                "우", "구", "신", "임", "나", "전", "민", "유", "진", "지", "엄", "채", "원", "천", "방", "공", "강", "현", "함", "변", "염", "양",
                "변", "여", "추", "노", "도", "소", "신", "석", "선", "설", "마", "길", "주", "연", "방", "위", "표", "명", "기", "반", "왕", "금",
                "옥", "육", "인", "맹", "제", "모", "장", "남", "탁", "국", "여", "진", "어", "은", "편", "구", "용", "가", "강", "건", "경", "고", "관", "광", "구", "규", "근", "기", "길", "나", "남", "노", "누", "다",
                "단", "달", "담", "대", "덕", "도", "동", "두", "라", "래", "로", "루", "리", "마", "만", "명", "무", "문", "미", "민", "바", "박",
                "백", "범", "별", "병", "보", "빛", "사", "산", "상", "새", "서", "석", "선", "설", "섭", "성", "세", "소", "솔", "수", "숙", "순",
                "숭", "슬", "승", "시", "신", "아", "안", "애", "엄", "여", "연", "영", "예", "오", "옥", "완", "요", "용", "우", "원", "월", "위",
                "유", "윤", "율", "으", "은", "의", "이", "익", "인", "일", "잎", "자", "잔", "장", "재", "전", "정", "제", "조", "종", "주", "준",
                "중", "지", "진", "찬", "창", "채", "천", "철", "초", "춘", "충", "치", "탐", "태", "택", "판", "하", "한", "해", "혁", "현", "형",
                "혜", "호", "홍", "화", "환", "회", "효", "훈", "휘", "희", "운", "모", "배", "부", "림", "봉", "혼", "황", "량", "린", "을", "비",
                "솜", "공", "면", "탁", "온", "디", "항", "후", "려", "균", "묵", "송", "욱", "휴", "언", "령", "섬", "들", "견", "추", "걸", "삼",
                "열", "웅", "분", "변", "양", "출", "타", "흥", "겸", "곤", "번", "식", "란", "더", "손", "술", "훔", "반", "빈", "실", "직", "흠",
                "흔", "악", "람", "뜸", "권", "복", "심", "헌", "엽", "학", "개", "롱", "평", "늘", "늬", "랑", "얀", "향", "울", "련");
        Collections.shuffle(name);
        recommends.addAll(interparkClient.searchBooks(name.get(0), categoryId));
        AtomicInteger j = new AtomicInteger();
        while(recommends.size()<count){
            recommends.addAll(interparkClient.searchBooks(name.get(j.incrementAndGet()), categoryId));
        }


        return recommends;
    }


    public HashSet<Book> recommend(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new SignException("invalid Id"));
        HashSet<Book> recommends = new HashSet<>();

        if(bookShelfService.getLikedBooksByMemberId(memberId).isEmpty()){
            recommends.addAll(randomGet(member.getPrefer(),10));
        }
        else{
            recommends.addAll(randomGet(member.getPrefer(),5));
        }

        if(member.getAge()>=60){
            recommends.addAll(randomGet(120,5));
        }
        else if(member.getAge()>=40&&member.getAge()<60){
            recommends.addAll(randomGet(117,3));
        }
        else if(member.getAge()>=30&&member.getAge()<40){
            recommends.addAll(randomGet(111,3));
        }
        else if(member.getAge()>=19&&member.getAge()<29){
            recommends.addAll(randomGet(115,3));
        }
        else{
            recommends.addAll(randomGet(108,1));
            recommends.addAll(randomGet(109,1));
            recommends.addAll(randomGet(110,1));
        }
        recommends.addAll(randomGet(102,1));
        recommends.addAll(randomGet(101,3));


        List<Book> similarAgeMemberLiked = similarAge(member);
        Collections.shuffle(similarAgeMemberLiked);
        if(similarAgeMemberLiked.size()<4){
            recommends.addAll(similarAgeMemberLiked.subList(0,similarAgeMemberLiked.size()-1));
        }
        else{
            recommends.addAll(similarAgeMemberLiked.subList(0,3));
        }

        List<Book> similarMemberLiked = similar(member);
        Collections.shuffle(similarMemberLiked);
        if(similarMemberLiked.size()<4){
            recommends.addAll(similarMemberLiked.subList(0,similarMemberLiked.size()-1));
        }
        else{
            recommends.addAll(similarMemberLiked.subList(0,3));
        }

        while(recommends.size()<30){
            recommends.addAll(randomGet(member.getPrefer(),1));
        }

        return recommends;
    }
}
