package com.springboot.blog.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
public class PostDtoV2 {
    private long id;

    // title should not be empty/null and have at least 2 characters
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    // post should not be empty/null and have at least 10 characters
    @NotEmpty
    @Size(min= 10, message = "Post description should have at least 10 characters")
    private String description;

    // content should not be empty/null
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
    private List<String> tags;
}
