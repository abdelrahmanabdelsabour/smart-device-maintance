package com.elcmal.service;



import com.elcmal.model.Blog;
import com.elcmal.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }
    // Get blogs by author
    public List<Blog> getBlogsByAuthor(String author) {
        return blogRepository.findByAuthor(author);
    }

    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public Blog updateBlog(Long id, Blog blogDetails) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));

        blog.setTitle(blogDetails.getTitle());
        blog.setContent(blogDetails.getContent());
        blog.setAuthor(blogDetails.getAuthor());
        blog.setUpdatedAt(blogDetails.getUpdatedAt());

        if (blogDetails.getImageUrl() != null) {
            blog.setImageUrl(blogDetails.getImageUrl());  // Update image URL if provided
        }

        return blogRepository.save(blog);
    }

    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
