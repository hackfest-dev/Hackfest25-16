package hf25_16.debugging_chickens.mental_health_backend.repository;


import hf25_16.debugging_chickens.mental_health_backend.model.Blog;
import hf25_16.debugging_chickens.mental_health_backend.model.BlogLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogLikeRepository extends JpaRepository<BlogLike, Integer> {
    boolean existsByBlogIdAndUserUserId(Integer blogId, Integer userId);
    Optional<BlogLike> findByBlogIdAndUserUserId(Integer blogId, Integer userId);//jpa convention its like find by User having UserId
    void deleteAllByBlog(Blog blog);
}