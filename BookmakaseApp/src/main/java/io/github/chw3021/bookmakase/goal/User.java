package io.github.chw3021.bookmakase.goal;

import java.util.HashMap;
import io.github.chw3021.bookmakase.signservice.domain.Member;

public class User extends Member {
    private HashMap<Integer,Integer> readquantity = new HashMap<>();
    //key는 카테고리 ID, value는 해당 카테고리 책들에 대한 독서량

    
    public User(String name, int age, String password) {
    }

    public int getQuantity(Integer categoryId) {
        return readquantity.getOrDefault(categoryId,0);
    }

    public void setQuantity(Integer categoryId, Integer quantity) {
        if(quantity<0) {
        	return;
        }
        this.readquantity.put(categoryId, quantity);
    }

    public void addQuantity(Integer categoryId, Integer add) {
        this.readquantity.computeIfPresent(categoryId, (k,v) -> v+add);
        if(add<0) {
        	return;
        }
        this.readquantity.putIfAbsent(categoryId, add);
    }

}
