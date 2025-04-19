package hf25_16.debugging_chickens.mental_health_backend.service;

import hf25_16.debugging_chickens.mental_health_backend.dto.blog.request.BlogRequestDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.blog.response.BlogResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.blog.response.BlogSummaryDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.blog.trending.TrendingBlogSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface BlogService {
    BlogResponseDTO createBlog(BlogRequestDTO blogRequestDTO, MultipartFile image) throws Exception;
    Optional<BlogResponseDTO> getBlogById(Integer blogId);
    BlogResponseDTO updateBlog(Integer blogId, BlogRequestDTO blogRequestDTO, MultipartFile image) throws Exception;
    void deleteBlog(Integer blogId) throws Exception;
    BlogResponseDTO updateBlogApprovalStatus(Integer blogId, boolean isApproved);
    BlogResponseDTO likeBlog(Integer blogId);
    BlogResponseDTO unlikeBlog(Integer blogId);
    Page<BlogSummaryDTO> getBlogsByApprovalStatus(String status, Pageable pageable);
    Page<TrendingBlogSummaryDTO> getTrendingBlogs(Integer userId, String title, Pageable pageable);
    Page<BlogSummaryDTO> filterBlogs(Integer userId, String title,Pageable pageable);
}