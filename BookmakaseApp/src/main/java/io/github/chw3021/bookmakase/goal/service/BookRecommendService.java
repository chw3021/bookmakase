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
        AtomicInteger j = new AtomicInteger(0);
        recommends.add(interparkClient.searchBooks(categoryId));

        while(recommends.size()<count){
            recommends.add(interparkClient.searchBooks(categoryId));
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
