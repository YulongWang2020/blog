package com.wyl.blog.service;

import com.wyl.blog.NotFoundException;
import com.wyl.blog.dao.CategoryRepository;
import com.wyl.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return categoryRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Type getTypeByName(String name){

        return categoryRepository.findByName(name);

    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return categoryRepository.findByTop(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = categoryRepository.findById(id).get();
        if (t == null){
            throw new NotFoundException("Category Not Exist");
        }
        BeanUtils.copyProperties(type,t);
        return categoryRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        if(categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        }
    }
}
