package at.ac.tuwien.sepm.assignment.individual.endpoint.dto;

import at.ac.tuwien.sepm.assignment.individual.util.Sexes;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

public class HorseDto {

    private Long id;
    private String name;
    private Sexes sex;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
    private String description;
    private Long favSportID;

    public HorseDto() {
    }

    public HorseDto(String name, Sexes sex, LocalDate dateOfBirth, String description, Long favSportID) {
        this.name = name;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.favSportID = favSportID;
    }

    public HorseDto(Long id, String name, Sexes sex, LocalDate dateOfBirth, String description, Long favSportID) {
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
        HorseDto horseDTO = (HorseDto) o;
        return Objects.equals(id, horseDTO.id) &&
            name.equals(horseDTO.name) &&
            sex == horseDTO.sex &&
            dateOfBirth.equals(horseDTO.dateOfBirth) &&
            Objects.equals(description, horseDTO.description) &&
            Objects.equals(favSportID, horseDTO.favSportID);
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
        return "HorseDTO{ " + this.fieldsString() + " }";
    }
}
