package com.fitnesstracker.fitnesstrackerapi.repositories;

import com.fitnesstracker.fitnesstrackerapi.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTests(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    public void UserRepository_Save_ReturnSavedUser() {
        User user = User.TestUser();

        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
        assertThat(savedUser.getWorkouts()).isNotNull();
    }

    @Test
    public void UserRepository_FindById_ReturnUserNotNull() {
        User user = User.TestUser();

        userRepository.save(user);
        User findUser = userRepository.findById(user.getId()).orElse(null);

        assertThat(findUser).isNotNull();
        assertThat(findUser.getWorkouts()).isNotNull();
    }

    @Test
    public void UserRepository_FindByEmail_ReturnUserNotNull() {
        User user = User.TestUser();

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void UserRepository_Update_ReturnUserNotNull() {
        User user = User.TestUser();
        userRepository.save(user);

        String updatedUsername = "new_username32145";
        Double updatedWeight = 70.0;

        user.setUsername(updatedUsername);
        user.setWeightKg(updatedWeight);

        User updatedUser = userRepository.save(user);

        assertThat(updatedUser.getUsername()).isNotNull();
        assertThat(updatedUser.getWeightKg()).isNotNull();
        assertThat(updatedUser.getUsername()).isEqualTo(updatedUsername);
        assertThat(updatedUser.getWeightKg()).isEqualTo(updatedWeight);
        assertThat(updatedUser.getWorkouts()).isNotNull();
    }

    @Test
    public void UserRepository_DeleteById_ReturnUserIsEmpty() {
        User user = User.TestUser();

        userRepository.save(user);

        userRepository.deleteById(user.getId());
        Optional<User> userReturn = userRepository.findById(user.getId());

        assertThat(userReturn).isEmpty();
    }
}
