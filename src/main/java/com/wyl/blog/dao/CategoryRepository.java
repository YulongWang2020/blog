package com.wyl.blog.dao;

import com.wyl.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Type,Long> {
    Type findByName(String name);

    @Query("select t from Type t")
    List<Type> findByTop(Pageable pageable);
}
