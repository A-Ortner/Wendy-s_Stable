package at.ac.tuwien.sepm.assignment.individual.entity;

import at.ac.tuwien.sepm.assignment.individual.util.Sexes;
import java.time.LocalDate;
import java.util.Objects;

public class Horse {

    private Long id;
    private String name;
    private Sexes sex;
    private LocalDate dateOfBirth;
    private String description;
    private Long favSportID;

    public Horse() {
    }

    public Horse(String name, Sexes sex, LocalDate dateOfBirth, String description, Long favSportID) {
        this.name = name;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.favSportID = favSportID;
    }

    public Horse(Long id, String name, Sexes sex, LocalDate dateOfBirth, String description, Long favSportID) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.favSportID = favSportID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sexes getSex() {
        return sex;
    }

    public void setSex(Sexes sex) {
        this.sex = sex;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFavSportID() {
        return favSportID;
    }

    public void setFavSportID(Long favSportID) {
        this.favSportID = favSportID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horse horse = (Horse) o;
        return Objects.equals(id, horse.id) &&
            name.equals(horse.name) &&
            sex == horse.sex &&
            dateOfBirth.equals(horse.dateOfBirth) &&
            Objects.equals(description, horse.description) &&
            Objects.equals(favSportID, horse.favSportID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sex, dateOfBirth, description, favSportID);
    }

    private String fieldsString() {
        return "id=" + id +
            ", name='" + name + '\'' +
            ", sex=" + sex +
            ", dateOfBirth=" + dateOfBirth +
            ", description='" + description + '\'' +
            ", favSport='" + favSportID + '\'';
    }

    @Override
    public String toString() {
        return "Horse{ " + this.fieldsString() + " }";
    }
}
