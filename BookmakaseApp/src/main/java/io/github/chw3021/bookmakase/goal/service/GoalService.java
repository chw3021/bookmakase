package io.github.chw3021.bookmakase.goal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.chw3021.bookmakase.goal.domain.BookGoal;
import io.github.chw3021.bookmakase.goal.dto.BookGoalDto;
import io.github.chw3021.bookmakase.goal.repository.BookGoalRepository;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import io.github.chw3021.bookmakase.signservice.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GoalService {

    @Autowired
    private final BookGoalRepository goalRepository;

    @Autowired
    private MemberRepository memberRepository;

    public BookGoal createGoal(BookGoalDto goalDto) throws Exception {
        try {
            BookGoal goal = BookGoal.builder()
                    .id(goalDto.getId())
                    .goalname(goalDto.getGoalname())
                    .member(goalDto.getMember(memberRepository))
                    .completed(goalDto.getCompleted())
                    .categoryId(goalDto.getCategoryId())
                    .startDate(goalDto.getStartDate())
                    .endDate(goalDto.getEndDate())
                    .targetQuantity(goalDto.getTargetQuantity())
                    .readed(goalDto.getReaded())
                    .build();
            return goalRepository.save(goal);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
    }


    public BookGoal updateGoal(BookGoalDto goalDto) throws Exception {

        BookGoal goal = getGoalByGoalname(goalDto.getGoalname());
        goal.setReaded(goalDto.getReaded());
        goal.setEndDate(goalDto.getEndDate());
        goal.setStartDate(goalDto.getStartDate());
        goal.setCategoryId(goalDto.getCategoryId());
        goal.setTargetQuantity(goalDto.getTargetQuantity());
        return goalRepository.save(goal);
    }


    public BookGoal getGoalByGoalname(String goalname) {
        BookGoal goal = goalRepository.findByGoalname(goalname)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find goal with goalname: " + goalname));
        return goal;
    }

    public BookGoal getGoalById(Long id) {
        BookGoal goal = goalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find goal with id: " + id));
        return goal;
    }

    public List<BookGoal> setAllMemberGoalReadedByCategoryId(Long memberId, Integer categoryId, Integer readedAdd) {
        List<BookGoal> goals = getUserGoals(memberId);
        goals.forEach(g -> {
            if(g.getCategoryId() == categoryId) {
                int readed = g.getReaded() + readedAdd;
                g.setReaded(readed);
                if(readed > g.getTargetQuantity()) {
                    g.setReaded(g.getTargetQuantity());
                    g.setCompleted(true);
                }
            }
        });
        return goalRepository.saveAll(goals);
    }

    public BookGoal setReadedById(Long id, Integer readed) {
        BookGoal g = goalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find goal with id: " + id));
        g.setReaded(readed);
        if(readed > g.getTargetQuantity()) {
            g.setReaded(g.getTargetQuantity());
        }
        return goalRepository.save(g);
    }

    public void deleteGoal(Long id) {
        goalRepository.deleteById(id);
    }

    public void deleteAllUserGoalsByMemberId(Long memberId) {
        getUserGoals(memberId).forEach(g -> {
            goalRepository.delete(g);
        });
    }

    public boolean isGoalExist(String goalname){
        return goalRepository.findByGoalname(goalname).isPresent();
    }

    public List<BookGoal> getUserGoals(Long memberId) {
        return goalRepository.findAllByMemberId(memberId);
    }

    public Long getCompletedCount(Long memberId){
        return goalRepository.findAllByMemberId(memberId).stream().filter(g -> g.isCompleted()).count();
    }
    public Double getSuccessRate(Long memberId){
        Long s = goalRepository.findAllByMemberId(memberId).stream().filter(g -> g.isCompleted()).count();
        Integer all = goalRepository.findAllByMemberId(memberId).size();
        if(all == 0) {
            return (double) 0;
        }
        return s/all*100.0;
    }
    //같은 나이대 대한 독서 목표를 완료한 사용자 수를 받는 메서드
    public double getAverageRateOfSimilar(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId).orElseThrow(()->
                new Exception("계정을 찾을 수 없습니다."));
        List<Member> usersInSameAgeRange = memberRepository.findAllByAgeBetween(member.getAge() - 5, member.getAge() + 5);
        Double sum = 0d;
        int count = 0;
        for (Member user : usersInSameAgeRange) {
            sum = sum + getSuccessRate(user.getId());
            count++;
        }
        return sum/count;
    }
    //같은 나이대 대한 독서 목표를 완료한 사용자 수를 받는 메서드
    public int getNumberOfSimilarChallengers(Long memberId) throws Exception {
        int numOfUsers = 0;
        Member member = memberRepository.findById(memberId).orElseThrow(()->
                new Exception("계정을 찾을 수 없습니다."));
        List<Member> usersInSameAgeRange = memberRepository.findAllByAgeBetween(member.getAge() - 5, member.getAge() + 5);
        for (Member user : usersInSameAgeRange) {
            List<BookGoal> bookGoals = getUserGoals(user.getId());
            for (BookGoal bookGoal : bookGoals) {
                if (!bookGoal.isCompleted()) {
                    numOfUsers++;
                    break;
                }
            }
        }
        return numOfUsers;
    }
    public int getNumberOfSimilarUsersCompleted(Long memberId) throws Exception {
        int numOfUsers = 0;
        Member member = memberRepository.findById(memberId).orElseThrow(()->
                new Exception("계정을 찾을 수 없습니다."));
        List<Member> usersInSameAgeRange = memberRepository.findAllByAgeBetween(member.getAge() - 5, member.getAge() + 5);
        for (Member user : usersInSameAgeRange) {
            List<BookGoal> bookGoals = getUserGoals(user.getId());
            for (BookGoal bookGoal : bookGoals) {
                if (bookGoal.isCompleted()) {
                    numOfUsers++;
                    break;
                }
            }
        }
        return numOfUsers;
    }

    //같은 나이대, 같은 카테고리에 대한 독서 목표를 완료한 사용자 수를 받는 메서드
    public int getNumberOfSimilarChallengers(Long memberId, Integer categoryId) throws Exception {
        int numOfUsers = 0;
        Member member = memberRepository.findById(memberId).orElseThrow(()->
        new Exception("계정을 찾을 수 없습니다."));
        List<Member> usersInSameAgeRange = memberRepository.findAllByAgeBetween(member.getAge() - 5, member.getAge() + 5);
        for (Member user : usersInSameAgeRange) {
            List<BookGoal> bookGoals = getUserGoals(user.getId());
            for (BookGoal bookGoal : bookGoals) {
                if (bookGoal.getCategoryId()==categoryId && !bookGoal.isCompleted()) {
                    numOfUsers++;
                    break;
                }
            }
        }
        return numOfUsers;
    }
    public int getNumberOfSimilarUsersCompleted(Long memberId, Integer categoryId) throws Exception {
        int numOfUsers = 0;
        Member member = memberRepository.findById(memberId).orElseThrow(()->
                new Exception("계정을 찾을 수 없습니다."));
        List<Member> usersInSameAgeRange = memberRepository.findAllByAgeBetween(member.getAge() - 5, member.getAge() + 5);
        for (Member user : usersInSameAgeRange) {
            List<BookGoal> bookGoals = getUserGoals(user.getId());
            for (BookGoal bookGoal : bookGoals) {
                if (bookGoal.getCategoryId()==categoryId && bookGoal.isCompleted()) {
                    numOfUsers++;
                    break;
                }
            }
        }
        return numOfUsers;
    }
}