package com.wyl.blog.service;

import com.wyl.blog.dao.ProfileRepository;
import com.wyl.blog.po.Blog;
import com.wyl.blog.po.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    @Transactional
    @Override
    public Page<Profile> getProfile(Pageable pageable) {
        return profileRepository.findAll(pageable);
    }

    @Override
    public List<String> listSkills(String skills) {
        return stringToList(skills);
    }

    private List<String> stringToList(String s){
        List<String> stringList = new ArrayList<String>(Arrays.asList(s.split(",")));
        return stringList;
    }

    @Transactional
    @Override
    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }
}
