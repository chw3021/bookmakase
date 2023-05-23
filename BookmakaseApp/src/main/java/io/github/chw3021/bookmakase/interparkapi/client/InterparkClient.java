package io.github.chw3021.bookmakase.interparkapi.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.bookdata.domain.BookDto;
import io.github.chw3021.bookmakase.bookdata.domain.RecommendData;
import io.github.chw3021.bookmakase.bookdata.repository.RecommendDataRepository;
import io.github.chw3021.bookmakase.interparkapi.properties.InterparkProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
@Slf4j
@Component
public class InterparkClient {
    private final InterparkProperties properties;
    private final WebClient webClient;
    private ObjectMapper objectMapper = new ObjectMapper();
    

    @Autowired
    private final RecommendDataRepository recommendDataRepository;

    public BookDto getPopularBooks(int category_num) {//베스트셀러
        return convertToResponse(getPopularBooksFromApi(category_num));
    }

    private String getPopularBooksFromApi(int categoryId) {
        String items = null;
        try {
            items = webClient.get()
                    .uri(builder -> builder.path("/bestSeller.api")
                            .queryParam("categoryId", categoryId)
                            .queryParam("output", "json")
                            .queryParam("key", properties.getKey()).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class).block();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return items;
    }
    
    

    public BookDto getRecommendedBooks(int category_num) {//추천도서
        return convertToResponse(getRecommendedBooksFromApi(category_num));
    }

    private String getRecommendedBooksFromApi(int categoryId) {
        String items = null;
        try {
            items = webClient.get()
                    .uri(builder -> builder.path("/recommend.api")
                            .queryParam("categoryId", categoryId)
                            .queryParam("output", "json")
                            .queryParam("key", properties.getKey()).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class).block();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return items;
    }

    

    public BookDto getNewBooks(int category_num) {//신간도서
        return convertToResponse(getNewBooksFromApi(category_num));
    }

    private String getNewBooksFromApi(int categoryId) {
        String items = null;
        try {
            items = webClient.get()
                    .uri(builder -> builder.path("/newBook.api")
                            .queryParam("categoryId", categoryId)
                            .queryParam("output", "json")
                            .queryParam("key", properties.getKey()).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class).block();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return items;
    }


    public BookDto getBookSearchResultsByItemId(Long itemId) {//도서 검색
        return convertToResponse(getBookSearchResultsByItemIdFromApi(itemId));
    }

    private String getBookSearchResultsByItemIdFromApi(Long itemId) {
        String items = null;
        try {
            items = webClient.get()
                    .uri(builder -> builder.path("/search.api")
                            .queryParam("queryType", "productNumber")
                            .queryParam("query", itemId)
                            .queryParam("output", "json")
                            .queryParam("key", properties.getKey()).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class).block();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return items;
    }


    public BookDto getBookSearchResults(String query) {//도서 검색
        return convertToResponse(getBookSearchResultsFromApi(query));
    }

    private String getBookSearchResultsFromApi(String query) {
        String items = null;
        try {
            items = webClient.get()
                    .uri(builder -> builder.path("/search.api")
                            .queryParam("query", query)
                            .queryParam("output", "json")
                            .queryParam("key", properties.getKey()).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class).block();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return items;
    }
    
    
    
    private BookDto convertToResponse(String textData) {
        BookDto interparkResponseDto = new BookDto();
        try {
            interparkResponseDto = objectMapper.readValue(textData, BookDto.class);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return interparkResponseDto;
    }
    
    
    public List<Book> searchBooks(Integer categoryId, Integer count) {

        List<String> name = Arrays.asList("김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "한", "오", "서", "신", "권", "황", "안",
                "송", "류", "전", "홍", "고", "문", "양", "손", "배", "조", "백", "허", "유", "남", "심", "노", "정", "하", "곽", "성", "차", "주",
                "우", "구", "신", "임", "나", "전", "민", "유", "진", "지", "엄", "채", "원", "천", "방", "공", "강", "현", "함", "변", "염", "양",
                "변", "여", "추", "노", "도", "소", "신", "석", "선", "설", "마", "길", "주", "연", "방", "위", "표", "명", "기", "반", "왕", "금",
                "옥", "육", "인", "맹", "제", "모", "장", "남", "탁", "국", "여", "진", "어", "은", "편", "구", "용", "가", "건", "경", "고", 
                "관", "광", "구", "규", "근", "기", "길", "나", "남", "노", "누", "다", "제갈", "독고",
                "단", "달", "담", "대", "덕", "도", "동", "두", "라", "래", "로", "루", "리", "마", "만", "명", "무", "문", "미", "민", "바", "박",
                "백", "범", "별", "병", "보", "빛", "사", "산", "상", "새", "서", "석", "선", "설", "섭", "성", "세", "소", "솔", "수", "숙", "순",
                "숭", "슬", "승", "시", "신", "아", "안", "애", "엄", "여", "연", "영", "예", "오", "옥", "완", "요", "용", "우", "원", "월", "위",
                "유", "윤", "율", "으", "은", "의", "이", "익", "인", "일", "잎", "자", "잔", "장", "재", "전", "정", "제", "조", "종", "주", "준",
                "중", "지", "진", "찬", "창", "채", "천", "철", "초", "춘", "충", "치", "탐", "태", "택", "판", "하", "한", "해", "혁", "현", "형",
                "혜", "호", "홍", "화", "환", "회", "효", "훈", "휘", "희", "운", "모", "배", "부", "림", "봉", "혼", "황", "량", "린", "을", "비",
                "솜", "공", "면", "탁", "온", "디", "항", "후", "려", "균", "묵", "송", "욱", "휴", "언", "령", "섬", "들", "견", "추", "걸", "삼",
                "열", "웅", "분", "변", "양", "출", "타", "흥", "겸", "곤", "번", "식", "란", "더", "손", "술", "훔", "반", "빈", "실", "직", "흠",
                "흔", "악", "람", "뜸", "권", "복", "심", "헌", "엽", "학", "개", "롱", "평", "늘", "늬", "랑", "얀", "향", "울", "련", "a","b","c",
                "d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");
        Collections.shuffle(name);

        List<Book> allBooks = new ArrayList<>();
        AtomicInteger nameNum = new AtomicInteger(0);
        
        
        for(int i = 0; i<10; i++) {
            String items = null;
            List<Book> books = new ArrayList<Book>();
            if(recommendDataRepository.existsByQueryAndCategoryId(name.get(nameNum.get()), categoryId)){
            	List<RecommendData> rd = recommendDataRepository.findAllByQuery(name.get(nameNum.get()));
            	items = rd.stream().filter(r -> r.getCategoryId() == categoryId).findAny().get().getItems();
                if(convertToResponse(items).getItem() == null) {
                	continue;
                }
            }
            else {
                try {
                    items = webClient.get()
                            .uri(builder -> builder.path("/search.api")
                                    .queryParam("query", name.get(nameNum.get()))
                                    .queryParam("queryType", "author")
                                    .queryParam("categoryId", categoryId)
                                    .queryParam("output", "json")
                                    .queryParam("maxResults", 100)
                                    .queryParam("key", properties.getKey()).build())
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono(String.class).block();
                    RecommendData rd = RecommendData.builder()
                    		.categoryId(categoryId)
                    		.items(items)
                    		.query(name.get(nameNum.get()))
                    		.build();
                    recommendDataRepository.save(rd);

                    if(convertToResponse(items).getItem() == null) {
                    	continue;
                    }
                    
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
            }
            nameNum.incrementAndGet();
            
            BookDto bookDto = convertToResponse(items);
            books.addAll(bookDto.getItem());
            allBooks.addAll(books);

            try {
                Thread.sleep(10); // 일정 시간 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Collections.shuffle(allBooks);

        if(count>0 && allBooks.size()>count){
            return allBooks.subList(0, count);
        }
        
        return allBooks;
    }
}