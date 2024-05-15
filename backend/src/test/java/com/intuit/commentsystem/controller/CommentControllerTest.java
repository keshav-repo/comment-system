package com.intuit.commentsystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.commentsystem.document.ActionType;
import com.intuit.commentsystem.document.Reaction;
import com.intuit.commentsystem.document.ReactionType;
import com.intuit.commentsystem.dto.CommentDto;
import com.intuit.commentsystem.dto.ReactionDto;
import com.intuit.commentsystem.dto.ReactionParticipent;
import com.intuit.commentsystem.service.CommentService;
import com.intuit.commentsystem.service.ReactionService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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
    @MockBean
    private ReactionService reactionService;
    private ObjectMapper mapper;

    public CommentControllerTest() {
        mapper = new ObjectMapper();
    }

    @Test
    public void firstlevelcommentTest() throws Exception {
        int n = 2;
        List<CommentDto> mockCommentDtoList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            mockCommentDtoList.add(CommentDto.builder().content("comment " + (i + 1)).build());
        }
        when(commentService.firstlevelcomment(n)).thenReturn(mockCommentDtoList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/comment/firstLevel").param("n", String.valueOf(n)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();

        List<CommentDto> expectedComments = mapper.readValue(responseBody, new TypeReference<List<CommentDto>>() {
        });

        Assertions.assertEquals(n, expectedComments.size());
        for (int i = 0; i < n; i++) {
            CommentDto expectedCommentDto = mockCommentDtoList.get(i);
            CommentDto actualCommentDto = expectedComments.get(i);
            Assertions.assertEquals(expectedCommentDto.getContent(), actualCommentDto.getContent());
        }
    }

    @Test
    public void addCommentTest() throws Exception {
        CommentDto commentDto = CommentDto.builder()
                .content("comment 1")
                .commentId("1")
                .localDateTime(LocalDateTime.now())
                .postId("123")
                .build();

        when(commentService.addComment(commentDto)).thenReturn(commentDto);
        String jsonBody = mapper.writeValueAsString(commentDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        CommentDto commentDtoRes = mapper.readValue(responseBody, CommentDto.class);

        Assertions.assertEquals(commentDto.getContent(), commentDtoRes.getContent());
        Assertions.assertEquals(commentDto.getCommentId(), commentDtoRes.getCommentId());
    }

    @Test
    public void reactTest() throws Exception {
        ReactionDto reactionDto = ReactionDto.builder()
                .actionType(ActionType.LIKE.toString())
                .reactionType(ReactionType.COMMENT.toString())
                .refId("123")
                .userName("user1")
                .userId("7631")
                .build();
        String jsonBody = mapper.writeValueAsString(reactionDto);

        Mockito.doNothing().when(reactionService).react(any(Reaction.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/comment/react")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated());
    }

    @Test
    public void getReplyCommentTest() throws Exception {
        List<CommentDto> mockCommentDtoList = new ArrayList<>();
        mockCommentDtoList.add(CommentDto.builder().content("comment 1").build());
        mockCommentDtoList.add(CommentDto.builder().content("comment 2").build());

        when(commentService.getReply(any(String.class))).thenReturn(mockCommentDtoList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/comment/reply/6641ce70c557f063c1841a61"))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();

        List<CommentDto> expectedComments = mapper.readValue(responseBody, new TypeReference<List<CommentDto>>() {
        });

        Assertions.assertEquals(2, expectedComments.size());
        for (int i = 0; i < expectedComments.size(); i++) {
            CommentDto expectedCommentDto = mockCommentDtoList.get(i);
            CommentDto actualCommentDto = expectedComments.get(i);
            Assertions.assertEquals(expectedCommentDto.getContent(), actualCommentDto.getContent());
        }
    }

    @Test
    public void participantTest() throws Exception{
        List<ReactionParticipent> reactionParticipentList = new ArrayList<>();
        reactionParticipentList.add(ReactionParticipent.builder().userName("user1").build());
        reactionParticipentList.add(ReactionParticipent.builder().userName("user2").build());
        reactionParticipentList.add(ReactionParticipent.builder().userName("user3").build());

        when(reactionService.getReactionParticipant(any(ActionType.class), any(ReactionType.class), any(String.class)))
                .thenReturn(reactionParticipentList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/comment/participant")
                        .param("commentId","66421b38395aa53f39e8d2f9")
                        .param("actionType", "LIKE"))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();

        List<ReactionParticipent> expectedParticipants = mapper.readValue(responseBody, new TypeReference<List<ReactionParticipent>>() {});

        Assertions.assertEquals(3, expectedParticipants.size());
        for(int i=0; i< expectedParticipants.size(); i++){
            ReactionParticipent expectedParticipant = expectedParticipants.get(i);
            ReactionParticipent actualParticipent = reactionParticipentList.get(i);
            Assertions.assertEquals(expectedParticipant.getUserName(), actualParticipent.getUserName());
        }
    }

}
