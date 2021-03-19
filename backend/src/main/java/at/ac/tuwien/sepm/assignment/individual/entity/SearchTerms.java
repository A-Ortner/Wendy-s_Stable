package at.ac.tuwien.sepm.assignment.individual.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SearchTerms {
    String name;
    LocalDate dateOfBirth;
    String sex;
    String description;
    Long favSportId;

    public SearchTerms(String name, String dateOfBirth, String sex, String description, Long favSportId) {

        if (name == null || name.isBlank() || name.equals("null")) this.name = null;
        else this.name = name;

        if (dateOfBirth == null || dateOfBirth.isBlank() || dateOfBirth.equals("null")) this.dateOfBirth = null;
        else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            //string to date
            LocalDate localDate = LocalDate.parse(dateOfBirth, dateTimeFormatter);
            this.dateOfBirth = localDate;
        }

        if (sex == null || sex.isBlank() || sex.equals("null")) this.sex = null;
        else this.sex = sex;

        if (description == null || description.isBlank()) this.description = null;
        else this.description = description;

        if (this.favSportId == null) this.favSportId = null;
        else this.favSportId = favSportId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public String getDescription() {
        return description;
    }

    public Long getFavSportId() {
        return favSportId;
    }
}
