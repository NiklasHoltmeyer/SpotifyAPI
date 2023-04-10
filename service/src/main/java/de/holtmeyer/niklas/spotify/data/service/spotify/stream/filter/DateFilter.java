package de.holtmeyer.niklas.spotify.data.service.spotify.stream.filter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;
import java.util.function.Predicate;

public class DateFilter {
    private static final int DATE_FULL_LENGTH = 10; //"yyyy-MM-dd".length();
    private static final int DATE_YEAR_MONTH_LENGTH = 7; //"yyyy-MM".length();
    private static final int DATE_YEAR_LENGTH = 4; // "yyyy".length();

    public static <T> Predicate<T> age(int maxAgeInDays, Function<T, String> getDateAsString, Function<T, String> getDatePrecision) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var now = LocalDate.parse(LocalDate.now().toString(), formatter);

        return track -> {
            var releaseDate = getDateAsString.apply(track);
            var releaseDatePrecision = getDatePrecision.apply(track);

            if(releaseDate == null){
                return false;
            }

            LocalDate releaseDateTime = parseDate(releaseDate, releaseDatePrecision, formatter);
            var ageInDays = now.toEpochDay() - releaseDateTime.toEpochDay();
            return ageInDays < maxAgeInDays;
        };
    }

    private static LocalDate parseDate(String date, String precision, DateTimeFormatter formatter){
        var fullDate = switch(precision) {
            case "year" -> String.format("%s-%s-%s", date, "01", "01");
            case "month" -> String.format("%s-%s", date, "01");
            case "day" -> date;
            default -> throw new RuntimeException("Could not Parse Date");
        };

        return LocalDate.parse(fullDate, formatter);
    }
}
