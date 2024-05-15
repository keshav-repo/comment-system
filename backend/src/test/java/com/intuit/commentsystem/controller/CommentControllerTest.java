package com.intuit.commentsystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.commentsystem.dto.CommentDto;
import com.intuit.commentsystem.service.CommentService;
import com.intuit.commentsystem.service.PostService;
import com.intuit.commentsystem.service.ReactionService;
import com.intuit.commentsystem.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentService commentService;

    @Test
    public void firstlevelcommentTest() throws Exception {
        int n = 2;
        List<CommentDto> mockCommentDtoList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            mockCommentDtoList.add( CommentDto.builder().content("comment "+(i+1)).build() );
        }
        when(commentService.firstlevelcomment(n)).thenReturn(mockCommentDtoList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/comment/firstLevel").param("n", String.valueOf(n)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();

        List<CommentDto> expectedComments = mapper.readValue(responseBody, new TypeReference<List<CommentDto>>() {});

        Assertions.assertEquals(n, expectedComments.size());
        for(int i=0; i<n; i++){
            CommentDto expectedCommentDto = mockCommentDtoList.get(i);
            CommentDto actualCommentDto = expectedComments.get(i);
            Assertions.assertEquals(expectedCommentDto.getContent(), actualCommentDto.getContent());
        }
    }
}
