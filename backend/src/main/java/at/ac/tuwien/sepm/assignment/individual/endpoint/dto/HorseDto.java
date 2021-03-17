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
    private Long parent1Id;
    private Long parent2Id;

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

    public HorseDto(Long id, String name, Sexes sex, LocalDate dateOfBirth, String description, Long favSportId, Long parent1Id, Long parent2Id) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.favSportId = favSportId;
        this.parent1Id = parent1Id;
        this.parent2Id = parent2Id;
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

    public Long getParent1Id() {
        return parent1Id;
    }

    public void setParent1Id(Long parent1Id) {
        this.parent1Id = parent1Id;
    }

    public Long getParent2Id() {
        return parent2Id;
    }

    public void setParent2Id(Long parent2Id) {
        this.parent2Id = parent2Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HorseDto horseDto = (HorseDto) o;
        return Objects.equals(id, horseDto.id) &&
            name.equals(horseDto.name) &&
            sex == horseDto.sex &&
            dateOfBirth.equals(horseDto.dateOfBirth) &&
            Objects.equals(description, horseDto.description) &&
            Objects.equals(favSportId, horseDto.favSportId) &&
            Objects.equals(parent1Id, horseDto.parent1Id) &&
            Objects.equals(parent2Id, horseDto.parent2Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sex, dateOfBirth, description, favSportId, parent1Id, parent2Id);
    }

    private String fieldsString() {
        return "id=" + id +
            ", name='" + name + '\'' +
            ", sex=" + sex +
            ", dateOfBirth=" + dateOfBirth +
            ", description='" + description + '\'' +
            ", favSport='" + favSportId + '\'' +
            ", parent1Id='" + parent1Id + '\'' +
            ", parent2Id='" + parent2Id + '\'';
    }

    @Override
    public String toString() {
        return "HorseDTO{ " + this.fieldsString() + " }";
    }
}
