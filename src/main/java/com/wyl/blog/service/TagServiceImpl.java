package com.wyl.blog.service;

import com.wyl.blog.NotFoundException;
import com.wyl.blog.dao.TagRepository;
import com.wyl.blog.po.Tag;
import com.wyl.blog.po.Type;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class TagServiceImpl implements TagService{
    @Autowired
    private TagRepository tagRepository;

    @Override
    @Transactional
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    @Transactional
    public Tag getTagById(Long id) {
        return tagRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Tag getTagByName(String name) {

        return tagRepository.findByName(name);
    }

    @Override
    @Transactional
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    @Transactional
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(convertToList(ids));
    }

    @Override
    @Transactional
    public List<Tag> listTagTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return tagRepository.findByTop(pageable);
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                //Check if there are unknown tags
                System.out.println(idarray[i]);
                if (StringUtils.isNumeric(idarray[i])){
                    if (tagRepository.existsById(new Long(idarray[i]))){
                        list.add(new Long(idarray[i]));
                    }
                    else{
                        Tag tag = new Tag();
                        tag.setName(idarray[i]);
                        tagRepository.save(tag);
                        list.add(tag.getId());
                    }
                }
                else{
                    Tag tag = new Tag();
                    tag.setName(idarray[i]);
                    tagRepository.save(tag);
                    list.add(tag.getId());
                }
            }
        }
        return list;
    }


    @Override
    @Transactional
    public Tag updateTag(Long id,Tag tag) {
        Tag t = tagRepository.findById(id).get();
        if (t == null){
            throw new NotFoundException("Category Not Exist");
        }
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }

    @Override
    public void deleteTag(Long id) {
        if(tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
        }
    }
}
