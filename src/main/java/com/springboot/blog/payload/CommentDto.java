package com.springboot.blog.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDto {
    private long id;

    // name should not be empty/null
    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    // email should not be emtpy/null and valid
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    // comment body should not be empty/null and be at least 10 characters
    @NotEmpty(message = "Comment body should not be null or empty")
    @Size(min = 10, message = "Comment body must be at least 10 characters")
    private String body;
}
