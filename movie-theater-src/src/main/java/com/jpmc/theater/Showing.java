package com.jpmc.theater;

import java.time.LocalDateTime;
import java.util.List;

import com.jpmc.theater.DiscountRule;

public class Showing {
    private Movie movie;
    private int sequenceOfTheDay;
    private LocalDateTime showStartTime;
    private DiscountCalculator discountCalculator;

    public Showing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;
        this.discountCalculator = new DiscountCalculator(
            List.of(
                new SequenceBasedMovieDiscountRule(this),
                new SpecialMovieDiscountRule(movie)
            )
        );
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getStartTime() {
        return showStartTime;
    }

    public boolean isSequence(int sequence) {
        return this.sequenceOfTheDay == sequence;
    }

    public double getMovieFee() {
        return movie.getTicketPrice();
    }

    public int getSequenceOfTheDay() {
        return sequenceOfTheDay;
    }

    private double calculateFee(int audienceCount) {
        double discount = discountCalculator.getDiscount();
        return (movie.getTicketPrice()-discount) * audienceCount;
    }
}
