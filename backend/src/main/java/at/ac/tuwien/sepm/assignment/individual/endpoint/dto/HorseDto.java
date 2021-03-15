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
    private Long favSportId;

    public HorseDto() {
    }

    public HorseDto(String name, Sexes sex, LocalDate dateOfBirth, String description, Long favSportId) {
        this.name = name;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.favSportId = favSportId;
    }

    public HorseDto(Long id, String name, Sexes sex, LocalDate dateOfBirth, String description, Long favSportId) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.favSportId = favSportId;
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

    public Long getFavSportId() {
        return favSportId;
    }

    public void setFavSportId(Long favSportId) {
        this.favSportId = favSportId;
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
            Objects.equals(favSportId, horseDTO.favSportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sex, dateOfBirth, description, favSportId);
    }

    private String fieldsString() {
        return "id=" + id +
            ", name='" + name + '\'' +
            ", sex=" + sex +
            ", dateOfBirth=" + dateOfBirth +
            ", description='" + description + '\'' +
            ", favSport='" + favSportId + '\'';
    }

    @Override
    public String toString() {
        return "HorseDTO{ " + this.fieldsString() + " }";
    }
}
