package com.intuit.commentsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.commentsystem.document.User;
import com.intuit.commentsystem.dto.UserDto;
import com.intuit.commentsystem.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    private ObjectMapper mapper;

    public UserControllerTest() {
        mapper = new ObjectMapper();
    }

    @Test
    public void addUserTest() throws Exception {
        UserDto userDto = UserDto.builder()
                .userName("user1")
                .userId("123")
                .build();
        Mockito.when(userService.addUser(any(User.class))).thenReturn(userDto);

        String jsonBody = mapper.writeValueAsString(userDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        UserDto userDtoRes = mapper.readValue(responseBody, UserDto.class);

        Assertions.assertEquals(userDtoRes.getUserId(), userDto.getUserId());
        Assertions.assertEquals(userDtoRes.getUserName(), userDto.getUserName());
    }

    @Test
    public void getUserTest() throws Exception{
        UserDto userDto = UserDto.builder()
                .userName("user1")
                .userId("123")
                .build();
        Mockito.when(userService.getUser(any(String.class))).thenReturn(userDto);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user")
                        .param("userName","user1"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        UserDto userDtoRes = mapper.readValue(responseBody, UserDto.class);

        Assertions.assertEquals(userDtoRes.getUserId(), userDto.getUserId());
        Assertions.assertEquals(userDtoRes.getUserName(), userDto.getUserName());
    }
}
