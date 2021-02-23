package com.wyl.blog.web.admin;

import com.wyl.blog.po.Tag;
import com.wyl.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public  String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/addtag";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTagById(id));
        return "admin/addtag";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            return "admin/addtag";
        }
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null){
            System.out.println(tag1);
            result.rejectValue("name","nameError","Name already exists");
            return "admin/addtag";
        }

        Tag t= tagService.saveTag(tag);
        if (t == null){
            attributes.addFlashAttribute("message","Add Failed");
        } else{
            attributes.addFlashAttribute("message","Add Succeeded");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, Long id, RedirectAttributes attributes){
        if (result.hasErrors()){
            return "admin/addtag";
        }
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null){
            System.out.println(tag1);
            result.rejectValue("name","nameError","Name already exists");
            return "admin/addtag";
        }

        Tag t= tagService.updateTag(id,tag);
        if (t == null){
            attributes.addFlashAttribute("message","Update Failed");
        } else{
            attributes.addFlashAttribute("message","Update Succeeded");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String deletePost(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","Delete Succeeded");
        return "redirect:/admin/tags";

    }
}
