package com.jpmc.theater;

import com.jpmc.theater.Movie;
import com.jpmc.theater.Showing;

public interface DiscountRule{
    public double fetchDiscount(); 
}

class SpecialMovieDiscountRule implements DiscountRule{
    private Movie movie;
    private static double SPECIAL_MOVIE_DISCOUNT = 0.2;
    public SpecialMovieDiscountRule(Movie movie) {
        this.movie = movie;
    }
    @Override
    public double fetchDiscount() {
        if (movie.isSpecialMovie()){
            return movie.getTicketPrice()*SPECIAL_MOVIE_DISCOUNT;
        }
        return 0;
    }

}

class SequenceBasedMovieDiscountRule implements DiscountRule{
    private Showing showing;
    public SequenceBasedMovieDiscountRule(Showing showing) {
        this.showing = showing;
    }
    @Override
    public double fetchDiscount() {
        double sequenceDiscount = 0;
        int showSequence = this.showing.getSequenceOfTheDay();
        if (showSequence == 1) {
            sequenceDiscount = 3; // $3 discount for 1st show
        } else if (showSequence == 2) {

            sequenceDiscount = 2; // $2 discount for 2nd show
        }
        return sequenceDiscount;
    }

}

class ShowTimingDiscountRule implements DiscountRule{
    private Showing showing;
    public ShowTimingDiscountRule(Showing showing) {
        // super();
        this.showing = showing;
    }
    @Override
    public double fetchDiscount() {
        return 0;
    }

}

class DateBasedDiscountRule implements DiscountRule{
    private Showing showing;
    public DateBasedDiscountRule(Showing showing) {
        // super();
        this.showing = showing;
    }
    @Override
    public double fetchDiscount() {
        return 0;
    }

}