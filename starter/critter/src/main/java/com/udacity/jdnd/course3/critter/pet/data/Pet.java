package com.udacity.jdnd.course3.critter.pet.data;

import com.udacity.jdnd.course3.critter.user.data.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private PetType type;
    private String name;
    @ManyToOne()
    private Customer owner;
    private LocalDate birthDate;
    private String notes;
}
