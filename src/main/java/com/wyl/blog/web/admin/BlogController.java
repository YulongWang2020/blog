package com.wyl.blog.web.admin;

import com.wyl.blog.po.Blog;
import com.wyl.blog.po.User;
import com.wyl.blog.service.BlogService;
import com.wyl.blog.service.CategoryService;
import com.wyl.blog.service.TagService;
import com.wyl.blog.utils.FileUtils;
import com.wyl.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT ="admin/blogpublish";
    private static final String LIST ="admin/blogs";
    private static final String REDIRECT_LIST ="redirect:/admin/blogs";

    @Value("${upload.path}")
    private String path;

    @Value("${virtual.images.path.plant}")
    private String virtualPath;

    @Autowired
    private Environment environment;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size=10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        model.addAttribute("categories",categoryService.listType());
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String saerch(@PageableDefault(size=10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    private void setTypeAndTag(Model model){
        model.addAttribute("categories",categoryService.listType());
        model.addAttribute("tags",tagService.listTag());
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        if (blog.getFirstPicture().equals("/images/defaultFirstPicture.jpg")){
            blog.setFirstPicture(null);
        }
        model.addAttribute("blog",blogService.getBlog(id));
        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session, @RequestParam("file") MultipartFile file) throws FileNotFoundException {
        System.out.println(file);
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(categoryService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;

        if(blog.getFlag().equals("")){
            blog.setFlag("original");
        }
        if(blog.getFirstPicture().equals("")){
            blog.setFirstPicture("/images/defaultFirstPicture.jpg");
        }else{
//            upload file
            FileUtils fileUtils = new FileUtils(environment);
            String temp ="";
            String fileName = fileUtils.upload(file,file.getOriginalFilename());
            if (fileName != null ){
                temp = virtualPath+fileName;
                blog.setFirstPicture(temp);
                System.out.println("succeed");
            }
        }
        if(blog.getId()==null){
            b = blogService.saveBlog(blog);
        }
        else{
            System.out.println("update------------------");
            b = blogService.updateBlog(blog.getId(),blog);
        }
        if(b==null){
            attributes.addFlashAttribute("message","Operation Failed");
        }
        else{
            attributes.addFlashAttribute("message","Operation Succeeded");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","Delete Succeeded");
        return REDIRECT_LIST;
    }
}
