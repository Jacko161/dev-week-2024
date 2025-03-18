package jfleming.devweek.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
public class Person {
    @Id
    private UUID id;
    private String name;
    private String email;

    public Person(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
