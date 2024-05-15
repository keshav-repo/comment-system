package com.intuit.commentsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.commentsystem.dto.PostDto;
import com.intuit.commentsystem.service.PostService;
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
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private ObjectMapper mapper;

    public PostControllerTest() {
        mapper = new ObjectMapper();
    }

    @Test
    public void addUserTest() throws Exception {
        PostDto postDto = PostDto.builder()
                .content("post 1")
                .postId("123")
                .build();

        Mockito.when(postService.addPost(any(PostDto.class))).thenReturn(postDto);
        String jsonBody = mapper.writeValueAsString(postDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        PostDto postDtoRes = mapper.readValue(responseBody, PostDto.class);

        Assertions.assertEquals(postDtoRes.getPostId(), postDto.getPostId());
        Assertions.assertEquals(postDtoRes.getContent(), postDto.getContent());
    }

    @Test
    public void getPostTest() throws Exception {
        PostDto postDto = PostDto.builder()
                .content("post 1")
                .postId("123")
                .build();
        Mockito.when(postService.getPost(any(String.class))).thenReturn(postDto);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/post/123"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        PostDto postDtoRes = mapper.readValue(responseBody, PostDto.class);

        Assertions.assertEquals(postDtoRes.getPostId(), postDto.getPostId());
        Assertions.assertEquals(postDtoRes.getContent(), postDto.getContent());
    }

}
