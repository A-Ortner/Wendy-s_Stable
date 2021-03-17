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
    private Long favSportId;
    private Long parent1Id;
    private Long parent2Id;

    public Horse() {
    }

    public Horse(String name, Sexes sex, LocalDate dateOfBirth, String description, Long favSportId) {
        this.name = name;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.favSportId = favSportId;
    }

    public Horse(Long id, String name, Sexes sex, LocalDate dateOfBirth, String description, Long favSportId) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.favSportId = favSportId;
    }

    public Horse(Long id, String name, Sexes sex, LocalDate dateOfBirth, String description, Long favSportId, Long parent1Id, Long parent2Id) {
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
        Horse horse = (Horse) o;
        return Objects.equals(id, horse.id) &&
            name.equals(horse.name) &&
            sex == horse.sex &&
            dateOfBirth.equals(horse.dateOfBirth) &&
            Objects.equals(description, horse.description) &&
            Objects.equals(favSportId, horse.favSportId) &&
            Objects.equals(parent1Id, horse.parent1Id) &&
            Objects.equals(parent2Id, horse.parent2Id);
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
        return "Horse{ " + this.fieldsString() + " }";
    }
}
