package com.intuit.commentsystem;

import com.intuit.commentsystem.document.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void run(String... args) throws Exception {

		// save a post
//		Post post = Post.builder()
//				.content("some post")
//				.build();
//		post = mongoTemplate.save(post);
//		System.out.println(post.getPostId());

		String postId = "664181a78c79e154b8f05144";

		// create a comment
//		Comment comment = Comment.builder()
//				.content("some comment")
//				.parentId(null)
//				.postId(postId)
//				.localDateTime(LocalDateTime.now())
//				.postId(postId)
//				.build();
//
//		comment =  mongoTemplate.save(comment);
//		System.out.println(comment.getCommentId());

		String commentId = "6641827f30577a42a3133fa3";

		// create a user
//		User user = User.builder()
//				.userName("user1")
//				.build();
//		mongoTemplate.save(user);
//		System.out.println(user.getUserId());

		String userId = "6641848a93a3c0531887bda3";

		// add a like
		Reaction reaction = CommentReaction.builder()
				.userId(userId)
				.dateTime(LocalDateTime.now())
				.reactionType(ReactionType.LIKE)
				.commentId(commentId)
				.build();
		mongoTemplate.save(reaction);


	}
}
