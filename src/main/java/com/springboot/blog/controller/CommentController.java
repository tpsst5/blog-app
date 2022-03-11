package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@ApiResponse(description = "CRUD REST APIs for Comment resources")
@RestController
@RequestMapping("/api/v1")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(description = "Create Comment REST API")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                    @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @Operation(description = "Get all Comments by post ID REST API")
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @Operation(description = "Get single Comment by ID REST API")
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long postId,
                                                     @PathVariable(value = "id") long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @Operation(description = "Update comment by ID REST API")
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") long postId,
                                                    @PathVariable(value = "id") long commentId,
                                                    @Valid @RequestBody CommentDto commentDto) {

        return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentDto));
    }

    @Operation(description = "Delete Comment by ID REST API")
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable(value = "postId") long postId,
                                                    @PathVariable(value = "id") long commentId) {

        commentService.deleteCommentById(postId, commentId);

        return ResponseEntity.ok("Comment entity deleted successfully.");
    }
}
