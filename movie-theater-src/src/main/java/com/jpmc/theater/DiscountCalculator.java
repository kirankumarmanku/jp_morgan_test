package com.jpmc.theater;
import java.util.List;
import com.jpmc.theater.DiscountRule;

public class DiscountCalculator {
    private List<DiscountRule> discountRules;
    public DiscountCalculator(List<DiscountRule> discountRules) {
        super();
        this.discountRules = discountRules;
    }

    public double getDiscount(){
        double discount = 0;
        for (DiscountRule discountRule : this.discountRules) {
            if(discountRule.fetchDiscount() > discount){
                discount = discountRule.fetchDiscount();
            }
        }
        return discount;
    }
}
