package com.wyl.blog.web;

import com.wyl.blog.po.Profile;
import com.wyl.blog.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AboutShowController {
    @Autowired
    private ProfileService profileService;

    @Value("${profile.github}")
    private String github;

    @Value("${profile.facebook}")
    private String facebook;

    @Value("${profile.ins}")
    private String ins;


    @GetMapping("/about")
    public String about(Pageable pageable, Model model){
        Profile profile = profileService.getProfile(pageable).getContent().get(0);
        model.addAttribute("picture",profile.getPicture());
        model.addAttribute("skills",profileService.listSkills(profile.getSkills()));
        model.addAttribute("aboutMe",profile.getAboutMe());
        model.addAttribute("github",github);
        model.addAttribute("facebook",facebook);
        model.addAttribute("ins",ins);
        return "aboutme";
    }
}
