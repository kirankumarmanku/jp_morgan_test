# Introduction

This is a poorly written application, and we're expecting the candidate to greatly improve this code base.

## Instructions
* **Consider this to be your project! Feel free to make any changes**
* There are several deliberate design, code quality and test issues in the current code, they should be identified and resolved
* Implement the "New Requirements" below
* Keep it mind that code quality is very important
* Focus on testing, and feel free to bring in any testing strategies/frameworks you'd like to implement
* You're welcome to spend as much time as you like, however, we're expecting that this should take no more than 2 hours

## `movie-theater`

### Current Features
* Customer can make a reservation for the movie
  * And, system can calculate the ticket fee for customer's reservation
* Theater have a following discount rules
  * 20% discount for the special movie
  * $3 discount for the movie showing 1st of the day
  * $2 discount for the movie showing 2nd of the day
* System can display movie schedule with simple text format

## New Requirements
* New discount rules; In addition to current rules
  * Any movies showing starting between 11AM ~ 4pm, you'll get 25% discount
  * Any movies showing on 7th, you'll get 1$ discount
  * The discount amount applied only one if met multiple rules; biggest amount one
* We want to print the movie schedule with simple text & json format

## New Implementation Details:
### Calculating new discounts:
We can have DiscountCalculator.
This class will be composed of list of DiscountRule. Each rule is responsible for handling particular discount type. 
* Current Rules:
    1. SpecialMovieDiscountRule(movie)
    2. SequenceBasedMovieDiscountRule(show)

* New Rules:
    1. ShowTimingDiscountRule(show)
    2. DateBasedDiscountRule(show)
    3. Max of all discounts. 
    
### Providing JSON and Text options to print:
 I have added to methods in Showing class to print in Readable text and Json format. We can have dedicated Formatter class to handle different formats and provide it to Theatre class in future to make it more modular and maintanable in future. I have used com.fastxml.jackson library to serialize objects to JSON files and added these dependencies in pom.xml file. 
    
