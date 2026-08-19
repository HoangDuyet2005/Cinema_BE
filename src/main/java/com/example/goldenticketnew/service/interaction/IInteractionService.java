package com.example.goldenticketnew.service.interaction;

import com.example.goldenticketnew.dtos.CommentDto;
import com.example.goldenticketnew.dtos.LikeDto;
import com.example.goldenticketnew.payload.interaction.request.AddNewCommentRequest;
import com.example.goldenticketnew.payload.interaction.request.AddNewLikeRequest;
import com.example.goldenticketnew.payload.interaction.request.CheckUserLikeRequest;
import com.example.goldenticketnew.payload.interaction.request.UpdateCommentRequest;
import com.example.goldenticketnew.payload.response.ApiResponse;
import com.example.goldenticketnew.payload.response.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IInteractionService {
    PageResponse<CommentDto> getAllComment(Long articleId, Pageable pageable) ;
    List<LikeDto> getAllLike() ;
    List<LikeDto> getAllLikeByArticle(Long articleId) ;

    CommentDto addNewComment(AddNewCommentRequest request);
    CommentDto updateComment(UpdateCommentRequest request);
    ApiResponse deleteComment(Long id);

    LikeDto addNewLike(AddNewLikeRequest request);

    ApiResponse checkUserLike(CheckUserLikeRequest request);
}
