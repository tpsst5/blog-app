package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl  implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        // retrieve post entity by id
        Post post = getPostById(postId);

        // set post to comment entity
        comment.setPost(post);

        // save comment entity to DB
        Comment newComment = commentRepository.save(comment);

        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {

        // retrieve comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId); // change?

        // convert list of comment entities to list of comment dto's
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        // retrieve post entity by id
        Post post = getPostById(postId);

        // get comment by id from the database
        Comment comment = getCommentById(commentId);

        // throw exception if comment is invalid for post
        checkForValidComment(post, comment);

        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentRequest) {
        // retrieve post entity by id
        Post post = getPostById(postId);

        // get comment by id from the database
        Comment comment = getCommentById(commentId);

        // throw exception if comment is invalid for post
        checkForValidComment(post, comment);

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return mapToDTO(updatedComment);
    }

    @Override
    public CommentDto deleteCommentById(long postId, long commentId) {
        // retrieve post entity by id
        Post post = getPostById(postId);

        // get comment by id from the database
        Comment comment = getCommentById(commentId);

        // throw exception if comment is invalid for post
        checkForValidComment(post, comment);

        commentRepository.delete(comment);

        return mapToDTO(comment);
    }

    private Post getPostById(long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
    }

    private Comment getCommentById(long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));
    }

    private void checkForValidComment(Post post, Comment comment) {
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post.");
        }
    }

    // Convert DTO to entity
    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);

//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());

        return comment;
    }

    // Convert entity to DTO
    private CommentDto mapToDTO(Comment comment) {
        CommentDto commentDto = mapper.map(comment, CommentDto.class);

//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());

        return commentDto;
    }
}
