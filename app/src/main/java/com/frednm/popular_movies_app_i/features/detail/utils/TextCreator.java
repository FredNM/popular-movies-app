package com.frednm.popular_movies_app_i.features.detail.utils;

/**
 * Instead of creating Custom Data Binding on TextView, use the usual android:text, and call methods of this class to do
 * the text modifications needed.
 * See https://medium.com/androiddevelopers/data-binding-lessons-learnt-4fd16576b719
 */

public class TextCreator {

    // Take the date and return the year. For example, take 2019-5-30, and return 2019
    public String extractYear(String date) {
        if (date != null && !date.isEmpty()) {
            return date.substring(0, 4);
        } else {
            return null;
        }
    }

    // Take duration and return it with "min" at the end. For example, take 120, and return 120 min
    public String durationInMin (Integer duration) {
        return duration+" min";
    }

    // Take vote average, and return it with /10. For example, take 8.1, and return 8.1/10
    public String voteAverage(Double vote) {
        return vote+"/10";
    }
}
