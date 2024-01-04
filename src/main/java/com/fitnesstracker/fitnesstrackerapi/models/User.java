package com.fitnesstracker.fitnesstrackerapi.models;

import com.fitnesstracker.fitnesstrackerapi.dtos.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private LocalDate dob;
    private Double heightCm;
    private Double weightKg;

    public UserDTO userDTO() {
        return new UserDTO(id, username, dob, heightCm, weightKg);
    }

    public static User TestUser() {
        return User.builder()
                .username("testing_username123")
                .email("john.doe123@example.com")
                .password("strongPassword?/14zT")
                .dob(LocalDate.parse("2002-11-05"))
                .heightCm(179.5)
                .weightKg(75.5).build();
    }
}
