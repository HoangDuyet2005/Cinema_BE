package com.example.goldenticketnew.dtos;


import com.example.goldenticketnew.model.User;
import com.example.goldenticketnew.model.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends DateAudit {
    private Long id;
    private String username;
    private String email;
    private String name;
    private String image;
    private String role;

    private String bio;

    private Integer followers;

    private Long amountArticle ;

    private String reason;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.name = user.getName();
        if(user.getImage() != null)  this.image = user.getImage();
        this.role = user.getRoles().stream().findFirst().get().getName().toString();
        if(user.getBio() !=null)  this.bio = user.getBio();
//        IArticleRepository articleRepository = BeanUtils.getBean(IArticleRepository.class);
//        GetAllArticleRequest request = new GetAllArticleRequest();
//        request.setUsername(user.getUsername());
//        this.amountArticle = articleRepository.findAll(request.getSpecification()).size();
        }


}
