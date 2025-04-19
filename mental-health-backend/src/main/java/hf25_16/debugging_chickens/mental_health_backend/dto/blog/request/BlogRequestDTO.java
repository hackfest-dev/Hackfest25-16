package hf25_16.debugging_chickens.mental_health_backend.dto.blog.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogRequestDTO {
    private String title;
    private String content;
    private Integer userId;
    private Boolean isOpenForCommunication;
    private String summary;
}