package com.wyl.blog.dao;

import com.wyl.blog.po.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
