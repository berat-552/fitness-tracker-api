package com.fitnesstracker.fitnesstrackerapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnesstracker.fitnesstrackerapi.models.User;
import com.fitnesstracker.fitnesstrackerapi.services.User.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createUser_ReturnsUser() throws Exception {
        User user = User.TestUser();
        given(userService.createUser(any(User.class))).willReturn(user.userDTO());

        ResultActions result = mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.weightKg").value(user.getWeightKg()))
                .andExpect(jsonPath("$.heightCm").value(user.getHeightCm()))
                .andExpect(jsonPath("$.dob").value(user.getDob().toString()))
                .andReturn();
    }

    @Test
    public void getUser_ReturnsUser() throws Exception {
        User user = User.TestUser();
        given(userService.getUser(any(Long.class))).willReturn(user.userDTO());

        ResultActions result = mockMvc.perform(get("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.weightKg").value(user.getWeightKg()))
                .andExpect(jsonPath("$.heightCm").value(user.getHeightCm()))
                .andExpect(jsonPath("$.dob").value(user.getDob().toString()))
                .andReturn();
    }

    @Test
    public void updateUser_ReturnsUpdatedUser() throws Exception {
        User user = User.TestUser();

        given(userService.updateUser(any(Long.class), any(User.class))).willReturn(user.userDTO());

        ResultActions result = mockMvc.perform(put("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.weightKg").value(user.getWeightKg()))
                .andExpect(jsonPath("$.heightCm").value(user.getHeightCm()))
                .andExpect(jsonPath("$.dob").value(user.getDob().toString()))
                .andReturn();
    }

    @Test
    public void deleteUser_ReturnsHttpStatus200() throws Exception {
        User user = User.TestUser();

        given(userService.createUser(any(User.class))).willReturn(user.userDTO());
        userService.createUser(user);

        willDoNothing().given(userService).deleteUser(any(Long.class));

        ResultActions result = mockMvc.perform(delete("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }
}
