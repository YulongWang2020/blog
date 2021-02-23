package com.wyl.blog.web;

import com.wyl.blog.po.Blog;
import com.wyl.blog.po.Type;
import com.wyl.blog.service.BlogService;
import com.wyl.blog.service.CategoryService;
import com.wyl.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CategoryShowController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/categories/{id}")
    public String categories(@PageableDefault(size=7, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                             @PathVariable Long id,
                             Model model){
        List<Type> categories = categoryService.listTypeTop(1000);
        if (id == -1 && categories.size()>0){
            id = categories.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("categories",categories);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("activeCategoryId",id);
        return "categories";


    }


}
