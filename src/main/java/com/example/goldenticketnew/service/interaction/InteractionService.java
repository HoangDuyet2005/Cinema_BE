package com.example.goldenticketnew.service.interaction;

import com.example.goldenticketnew.dtos.CommentDto;
import com.example.goldenticketnew.dtos.LikeDto;
import com.example.goldenticketnew.enums.ResponseCode;
import com.example.goldenticketnew.exception.InternalException;
import com.example.goldenticketnew.model.*;
import com.example.goldenticketnew.payload.interaction.request.AddNewCommentRequest;
import com.example.goldenticketnew.payload.interaction.request.AddNewLikeRequest;
import com.example.goldenticketnew.payload.interaction.request.CheckUserLikeRequest;
import com.example.goldenticketnew.payload.interaction.request.UpdateCommentRequest;
import com.example.goldenticketnew.payload.response.ApiResponse;
import com.example.goldenticketnew.payload.response.PageResponse;
import com.example.goldenticketnew.repository.ICommentRepository;
import com.example.goldenticketnew.repository.ILikeRepository;
import com.example.goldenticketnew.service.article.ArticleService;
import com.example.goldenticketnew.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InteractionService implements IInteractionService{

    private final ILikeRepository likeRepository;
    private final ICommentRepository commentRepository;
    private final UserService userService;
    private final ArticleService articleService;


    @Override
    public PageResponse<CommentDto> getAllComment(Long articleId, Pageable pageable) {

        return new PageResponse<>(commentRepository.findAllByArticleId(articleId, pageable).map(CommentDto::new));
    }

    @Override
    public List<LikeDto> getAllLike() {
        return likeRepository.findAll(Sort.by("updatedAt").descending()).stream().map(LikeDto::new).collect(Collectors.toList());
    }
    @Override
    public List<LikeDto> getAllLikeByArticle(Long articleId) {
        return likeRepository.findAllByArticleId(articleId).stream().map(LikeDto::new).collect(Collectors.toList());
    }
    @Override
    public CommentDto addNewComment(AddNewCommentRequest request) {
        User user = userService.getUser(request.getUserId());
        Article article = articleService.getArticle(request.getArticleId());
        Comment comment = new Comment();
        comment.setArticle(article);
        comment.setUser(user);
        comment.setDescription(request.getDescription());
        return new CommentDto( commentRepository.save(comment));

    }

    @Override
    public CommentDto updateComment(UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(request.getId()).orElseThrow(() -> new InternalException(ResponseCode.COMMENT_NOT_FOUND));
        comment.setDescription(request.getDescription());
        return new CommentDto( commentRepository.saveAndFlush(comment));
    }

    @Override
    @Transactional
    public ApiResponse deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new InternalException(ResponseCode.COMMENT_NOT_FOUND));
        try {
            commentRepository.deleteByCommentId(id);
            return new ApiResponse(true, "Xoa comment thanh cong");
        }catch (Exception e){
            return new ApiResponse(false, e.getMessage());
        }

    }

    @Override
    public LikeDto addNewLike(AddNewLikeRequest request) {
        User user = userService.getUser(request.getUserId());
        Article article = articleService.getArticle(request.getArticleId());
        Like like = likeRepository.findFirstByUserIdAndArticleId(request.getUserId(), request.getArticleId());
        if(like != null){
            like.setIsLike(like.getIsLike() == 1 ? 0 : 1);
            return new LikeDto(likeRepository.saveAndFlush(like));
        }else {
            Like newLike = new Like();
            newLike.setIsLike(1);
            newLike.setUser(user);
            newLike.setArticle(article);
            return new LikeDto(likeRepository.save(newLike));
        }
    }

    @Override
    public ApiResponse checkUserLike(CheckUserLikeRequest request) {
        User user = userService.getUser(request.getUserId());
        Article article = articleService.getArticle(request.getArticleId());
        Like like = likeRepository.findByUserIdAndArticleIdAndIsLike(request.getUserId(), request.getArticleId());
        if(like != null){
            return new ApiResponse(true, "User đã like bài viết");
        }else {
            return new ApiResponse(false, "User chưa like bài viết");
        }
    }
}
