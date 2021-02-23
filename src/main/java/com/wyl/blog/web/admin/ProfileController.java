package com.wyl.blog.web.admin;

import com.wyl.blog.po.Profile;
import com.wyl.blog.service.ProfileService;
import com.wyl.blog.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@Controller
@RequestMapping("/admin")
public class ProfileController {
    @Value("${upload.path}")
    private String path;

    @Value("${virtual.images.path.plant}")
    private String virtualPath;

    @Autowired
    private Environment environment;

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profile")
    public String profile(Pageable pageable, Model model){
        Profile profile = profileService.getProfile(pageable).getContent().get(0);
        model.addAttribute("page",profileService.getProfile(pageable));
        model.addAttribute("skills",profileService.listSkills(profile.getSkills()));
        return "admin/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@RequestParam("file") MultipartFile file, Model model,Profile profile,Pageable pageable) throws FileNotFoundException {
        Profile p = profileService.getProfile(pageable).getContent().get(0);
        System.out.println(profile.toString());
        p.setAboutMe(profile.getAboutMe());
        p.setSkills(profile.getSkills());
        System.out.println(file+"file------------------------------");
        if(!file.isEmpty()){
            FileUtils fileUtils = new FileUtils(environment);
            String temp ="";
            String fileName = fileUtils.upload(file,file.getOriginalFilename());
            if (fileName != null ){
                temp = virtualPath+fileName;
                p.setPicture(temp);
                System.out.println("succeed");
            }
        }
        System.out.println(p.toString()+"-------------asdfffffff");
        profileService.saveProfile(p);
        return "redirect:/admin/profile";
    }
}
