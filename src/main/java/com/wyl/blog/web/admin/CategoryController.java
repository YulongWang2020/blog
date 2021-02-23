package com.wyl.blog.web.admin;

import com.sun.xml.bind.v2.TODO;
import com.wyl.blog.dao.CategoryRepository;
import com.wyl.blog.po.Type;
import com.wyl.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){

        model.addAttribute("page",categoryService.listType(pageable));
        return "admin/categories";
    }

    @GetMapping("/categories/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/addcategory";
    }

    @GetMapping("/categories/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",categoryService.getType(id));
        return "admin/addcategory";
    }

    @PostMapping("/categories")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            return "admin/addcategory";
        }
        Type type1 = categoryService.getTypeByName(type.getName());
        if (type1 != null){
            System.out.println(type1);
            result.rejectValue("name","nameError","Name already exists");
            return "admin/addcategory";
        }

        Type t= categoryService.saveType(type);
        if (t == null){
            attributes.addFlashAttribute("message","Add Failed");
        } else{
            attributes.addFlashAttribute("message","Add Succeeded");
        }
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/{id}")
    public String editPost(@Valid Type type, BindingResult result, Long id, RedirectAttributes attributes){
        if (result.hasErrors()){
            return "admin/addcategory";
        }
        Type type1 = categoryService.getTypeByName(type.getName());
        if (type1 != null){
            System.out.println(type1);
            result.rejectValue("name","nameError","Name already exists");
            return "admin/addcategory";
        }

        Type t= categoryService.updateType(id, type);
        if (t == null){
            attributes.addFlashAttribute("message","Update Failed");
        } else{
            attributes.addFlashAttribute("message","Update Succeeded");
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/{id}/delete")
    public String deletePost(@PathVariable Long id, RedirectAttributes attributes){
        categoryService.deleteType(id);
        attributes.addFlashAttribute("message","Delete Succeeded");
        return "redirect:/admin/categories";

    }

}
