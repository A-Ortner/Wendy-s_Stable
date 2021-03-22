package at.ac.tuwien.sepm.assignment.individual.endpoint.dto;

import at.ac.tuwien.sepm.assignment.individual.util.Sexes;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class TreeHorseDto {
    private Long id;
    private String name;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
    private Long parent1Id;
    private Long parent2Id;

    public TreeHorseDto(Long id, String name, LocalDate dateOfBirth, Long parent1Id, Long parent2Id) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.parent1Id = parent1Id;
        this.parent2Id = parent2Id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Long getParent1Id() {
        return parent1Id;
    }

    public Long getParent2Id() {
        return parent2Id;
    }
}
