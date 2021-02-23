package com.wyl.blog.service;


import com.wyl.blog.po.Blog;
import com.wyl.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvertBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(String query, Pageable pageable);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    Map<String,List<Blog>> timelineBlog();

    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);



}
