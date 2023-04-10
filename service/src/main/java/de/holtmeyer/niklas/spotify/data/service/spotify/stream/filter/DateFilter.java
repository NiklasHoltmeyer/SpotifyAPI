package de.holtmeyer.niklas.spotify.data.service.spotify.stream.filter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;
import java.util.function.Predicate;

public class DateFilter {
    private static final int DATE_FULL_LENGTH = 10; //"yyyy-MM-dd".length();
    private static final int DATE_YEAR_MONTH_LENGTH = 7; //"yyyy-MM".length();
    private static final int DATE_YEAR_LENGTH = 4; // "yyyy".length();

    public static <T> Predicate<T> age(int maxAgeInDays, Function<T, String> getDateAsString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var now = LocalDate.parse(LocalDate.now().toString(), formatter);

        return track -> {
            var releaseDate = getDateAsString.apply(track);

            if(releaseDate == null){
                return false;
            }

            LocalDate releaseDateTime = parseDate(releaseDate, formatter);
            var ageInDays = now.toEpochDay() - releaseDateTime.toEpochDay();
            return ageInDays < maxAgeInDays;
        };
    }

    private static LocalDate parseDate(String date, DateTimeFormatter formatter){
        var fullDate = switch(date.length()) {
            case DateFilter.DATE_FULL_LENGTH -> date;
            case DATE_YEAR_MONTH_LENGTH -> String.format("%s-%d", "01");
            case DATE_YEAR_LENGTH -> String.format("%s-%d-%d", "01", "01");
            default -> throw new RuntimeException("Could not Parse Date");
        };

        return LocalDate.parse(fullDate, formatter);
    }
}
