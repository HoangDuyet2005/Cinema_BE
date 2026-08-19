package com.example.goldenticketnew.service.article;

import com.example.goldenticketnew.dtos.ArticleDto;
import com.example.goldenticketnew.dtos.ArticleReportDto;
import com.example.goldenticketnew.enums.ArticleStatus;
import com.example.goldenticketnew.enums.ResponseCode;
import com.example.goldenticketnew.exception.InternalException;
import com.example.goldenticketnew.model.Article;
import com.example.goldenticketnew.model.User;
import com.example.goldenticketnew.payload.article.request.*;
import com.example.goldenticketnew.payload.response.PageResponse;
import com.example.goldenticketnew.repository.IArticleRepository;
import com.example.goldenticketnew.repository.UserRepository;
import com.example.goldenticketnew.security.UserPrincipal;
import com.example.goldenticketnew.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ArticleService implements IArticleService{

    private final IArticleRepository articleRepository;


    private final UserRepository userRepository;



    @Override
    public ArticleDto addNewArticle(AddNewArticleRequest request) {
        Article article = Article.builder()
            .brief(request.getBrief())
            .title(request.getTitle())
            .status(ArticleStatus.CREATE)
            .mainImage(request.getMainImage())
            .type(request.getType())
            .keyword(request.getKeyword())
            .build();
        article = articleRepository.save(article);
        return new ArticleDto(article);
    }

    @Override
    public ArticleDto addNewArticleReview(AddNewReviewRequest request) {
        Article article = Article.builder()
            .brief(request.getBrief())
            .title(request.getTitle())
            .status(ArticleStatus.CREATE)
            .mainImage(request.getMainImage())
            .type(request.getType())
            .description(request.getDescription())
            .keyword(request.getKeyword())
            .build();
        article = articleRepository.save(article);
        return new ArticleDto(article);
    }




    @Transactional
    @Override
    public ArticleDto updateArticle(UpdateArticleRequest request) {
        Article article = articleRepository.findById(request.getId()).orElseThrow(() -> new InternalException(ResponseCode.ARTICLE_NOT_FOUND));
        if(request.getBrief() !=null) article.setBrief(request.getBrief());
        if(request.getTitle()!=null) article.setTitle(request.getTitle());
        if(request.getDescription()!=null) article.setDescription(request.getDescription());
        if(request.getMainImage()!=null) article.setMainImage(request.getMainImage());
        if(request.getThumbnail()!=null) article.setThumbnail(request.getThumbnail());
        if(request.getKeyword()!=null) article.setKeyword(request.getKeyword());

        article = articleRepository.saveAndFlush(article);
        return new ArticleDto(article);
    }

    @Override
    public ArticleDto getDetailArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new InternalException(ResponseCode.ARTICLE_NOT_FOUND));
        return new ArticleDto(article);
    }

    @Override
    public ArticleDto getArticleBySLug(String slug) {
        Long value = Long.parseLong(slug.replaceAll("[^0-9]", ""));
        Article article = articleRepository.findById(value).orElseThrow(() -> new InternalException(ResponseCode.ARTICLE_NOT_FOUND));
        article.setView(article.getView()+1);
        return new ArticleDto(articleRepository.save(article));
    }

    @Override
    public ArticleDto changeStatusArticle(ChangeArticleStatusRequest request) {
        Article article = articleRepository.findById(request.getArticleId()).orElseThrow(()-> new InternalException(ResponseCode.ARTICLE_NOT_FOUND));
        article.setStatus(request.getStatus());
        article = articleRepository.save(article);
        return new ArticleDto(article);
    }

    @Override
    public PageResponse<ArticleDto> getAllArticlePaging(GetAllArticleRequest request) {
        Page<Article> articlePage = articleRepository.findAll(request.getSpecification(),request.getPageable());
        return new PageResponse<>(articlePage.map(ArticleDto::new));
    }

    @Override
    public List<ArticleDto> getAllArticle(GetAllArticleRequest request) {
        List<Article> articles = articleRepository.findAll(request.getSpecification());
        return articles.stream().map(ArticleDto::new).collect(Collectors.toList());


    }

    @Override
    public void deleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new InternalException(ResponseCode.ARTICLE_NOT_FOUND));
        article.setStatus(ArticleStatus.DELETE);
        articleRepository.save(article);
    }

    @Override
    public List<ArticleDto> getAllByUser(UserPrincipal currentUser, ArticleStatus status) {
            GetAllArticleRequest request = new GetAllArticleRequest();
            request.setUsername(currentUser.getUsername());
            request.setStatus(status);
            List<Article> articles = articleRepository.findAll(request.getSpecification());
            return articles.stream().map(ArticleDto::new).collect(Collectors.toList());
    }

    @Override
    public Article getArticle(Long id) {
        return  articleRepository.findById(id).orElseThrow(() -> new InternalException(ResponseCode.ARTICLE_NOT_FOUND));
    }

    @Override
    public ArticleReportDto getReport(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTimeMY = LocalDate.parse(dateTime, formatter);
        Long totalArticleInMonth = articleRepository.getTotalArticle(dateTimeMY.getYear(),dateTimeMY.getMonthValue());
        Long totalUserInMonth = userRepository.getTotalUserInMonth(dateTimeMY.getYear(),dateTimeMY.getMonthValue());
        dateTimeMY = dateTimeMY.minusMonths(1);
        Long previousArticleMonth = articleRepository.getTotalArticle(dateTimeMY.getYear(),dateTimeMY.getMonthValue());
        Long previousUserMonth = userRepository.getTotalUserInMonth(dateTimeMY.getYear(),dateTimeMY.getMonthValue());
        
        Double percentUser = null;
        if(previousUserMonth!=0){
            percentUser = (double) ((totalUserInMonth - previousUserMonth) /previousUserMonth)*100;
        }
        Double percentArticle = null;
        if(previousArticleMonth!=0){
            percentArticle  = (double) ((totalArticleInMonth - previousArticleMonth) /previousArticleMonth) *100;
        }
        return new ArticleReportDto(totalArticleInMonth,previousArticleMonth,percentArticle ,totalUserInMonth,previousUserMonth,percentUser) ;
    }

    @Override
    public List<ArticleDto> addNewArticleInuser(Long userId, Long articleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new InternalException(ResponseCode.USER_NOT_FOUND));
        List<Article> articles = user.getSaveArticles();
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new InternalException(ResponseCode.ARTICLE_NOT_FOUND));
        articles.add(article);
        user.setSaveArticles(articles);
        userRepository.save(user);
        return articles.stream().map(ArticleDto::new).collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> removeArticleInuser(Long userId, Long articleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new InternalException(ResponseCode.USER_NOT_FOUND));
        List<Article> articles = user.getSaveArticles();
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new InternalException(ResponseCode.ARTICLE_NOT_FOUND));
        articles.remove(article);
        user.setSaveArticles(articles);
        userRepository.save(user);
        return articles.stream().map(ArticleDto::new).collect(Collectors.toList());
    }

    @Override
    public PageResponse<ArticleDto> getAllArticlePagingInUser(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new InternalException(ResponseCode.USER_NOT_FOUND));
        List<Article> articles = user.getSaveArticles();
        return new PageResponse<>(PageUtils.convertListToPage(articles.stream().map(ArticleDto::new).filter(x->x.getStatus().equals(ArticleStatus.APPROVE)).collect(Collectors.toList()),pageable));
    }

}
