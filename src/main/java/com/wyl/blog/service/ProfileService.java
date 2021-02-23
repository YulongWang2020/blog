package com.wyl.blog.service;

import com.wyl.blog.po.Blog;
import com.wyl.blog.po.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProfileService {

    Page<Profile> getProfile(Pageable pageable);

    List<String> listSkills(String skills);

    Profile saveProfile(Profile profile);
}
