package com.wyl.blog.service;

import com.wyl.blog.po.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long id);

    Comment saveComment(Comment comment);
}
