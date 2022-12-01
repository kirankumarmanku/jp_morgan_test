package com.jpmc.theater;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
                new SpecialMovieDiscountRule(movie),
                new ShowTimingDiscountRule(this),
                new DateBasedDiscountRule(this)
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
    public double calculateFee(int audienceCount) {
        double discount = this.getDiscount();
        return (movie.getTicketPrice()-discount) * audienceCount;
    }
    public double getDiscount(){
        return discountCalculator.getDiscount();
    }
    
    public static String toJson(List<Showing> showings) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        mapper.setDateFormat(df);
        mapper.registerModule(new JavaTimeModule());
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.setMixInAnnotation(Movie.class, MovieMixin.class);
        mapper.registerModule(simpleModule);
        String newJsonData = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(showings);
        return newJsonData;
    }

    public static String toReadableText(List<Showing> showings, LocalDateProvider provider){
        StringBuilder sb = new StringBuilder();
        sb.append(provider.currentDate());
        sb.append('\n');
        sb.append("===================================================");
        sb.append('\n');
        showings.forEach(s ->
        sb.append(s.getSequenceOfTheDay() + ": " + s.getStartTime() + " " + s.getMovie().getTitle() + " " + humanReadableFormat(s.getMovie().getRunningTime()) + " $" + s.getMovieFee()+"\n")
        );
        sb.append("===================================================");
        sb.append('\n');
        return sb.toString();
    }
    public static String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    // (s) postfix should be added to handle plural correctly
    private static String handlePlural(long value) {
        if (value == 1) {
            return "";
        }
        else {
            return "s";
        }
    }
}

class MovieMixin{
    @JsonSerialize(using = MovieSerializer.class)
    public String runningTime;
}

class MovieSerializer extends JsonSerializer<Duration>{
    @Override
    public void serialize(Duration duration, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(Showing.humanReadableFormat(duration));
    }
}
