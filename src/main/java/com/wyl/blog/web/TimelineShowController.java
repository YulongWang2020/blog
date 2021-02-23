package com.wyl.blog.web;

import com.wyl.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TimelineShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/timeline")
    public String timeline(Model model){
        model.addAttribute("timelineMap",blogService.timelineBlog());
        model.addAttribute("blogCount", blogService.countBlog());
        return "timeline";
    }
}
