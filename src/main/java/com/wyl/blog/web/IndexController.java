package com.wyl.blog.web;

import com.wyl.blog.NotFoundException;
import com.wyl.blog.po.Blog;
import com.wyl.blog.service.BlogService;
import com.wyl.blog.service.CategoryService;
import com.wyl.blog.service.TagService;
import com.wyl.blog.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;


    @GetMapping("/")
    public String index(@PageableDefault(size=7, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model){
            model.addAttribute("page",blogService.listBlog(pageable));
            model.addAttribute("categories",categoryService.listTypeTop(6));
            model.addAttribute("tags",tagService.listTagTop(10));
            model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
            return "index";

    }

    @PostMapping("/search")
    public String search(@PageableDefault(size=7, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    };

    @GetMapping("/blogs/{id}")
    public String blog(@PathVariable Long id, Model model){
        Blog b = blogService.getBlog(id);
        System.out.println(b.getContent()+"=====================================");
        model.addAttribute("blog",b);
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        System.out.println(blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }
}
