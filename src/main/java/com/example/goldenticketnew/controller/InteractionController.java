package com.example.goldenticketnew.controller;

import com.example.goldenticketnew.dtos.CommentDto;
import com.example.goldenticketnew.dtos.LikeDto;
import com.example.goldenticketnew.payload.interaction.request.AddNewCommentRequest;
import com.example.goldenticketnew.payload.interaction.request.AddNewLikeRequest;
import com.example.goldenticketnew.payload.interaction.request.CheckUserLikeRequest;
import com.example.goldenticketnew.payload.interaction.request.UpdateCommentRequest;
import com.example.goldenticketnew.payload.response.ApiResponse;
import com.example.goldenticketnew.payload.response.PageResponse;
import com.example.goldenticketnew.payload.response.ResponseBase;
import com.example.goldenticketnew.service.interaction.InteractionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping(value="/api/interaction/v1", produces = "application/json")
@Tag(name = "InteractionController", description = "Thao tác với tương tác của người dùng")
public class InteractionController {
    private final InteractionService interactionService;


    @Operation(
        summary = "Like - unlike bài viết",
        description = "- Thích và hủy thích bài viết"
    )
    @PostMapping("/like/add")
    public ResponseEntity<ResponseBase<LikeDto>> createNewLike(@Valid @RequestBody AddNewLikeRequest request) {
        return new ResponseEntity<>(new ResponseBase<>(interactionService.addNewLike(request), 901, "tương tác like thành công"), HttpStatus.OK);
    }
    @Operation(
        summary = "Thêm bình luận",
        description = "- Thêm bình luận"
    )
    @PostMapping("/comment/add")
    public ResponseEntity<ResponseBase<CommentDto>> createNewComment(@Valid @RequestBody AddNewCommentRequest request) {
        return new ResponseEntity<>(new ResponseBase<>(interactionService.addNewComment(request) ,903, "Thêm mới bình luận thành công"), HttpStatus.OK);
    }
    @Operation(
        summary = "Chỉnh sửa bình luận",
        description = "- Chỉnh sửa bình luận"
    )
    @PutMapping("/comment/update")
    public ResponseEntity<ResponseBase<CommentDto>> updateComment(@Valid @RequestBody UpdateCommentRequest request) {
        return new ResponseEntity<>(new ResponseBase<>(interactionService.updateComment(request) ), HttpStatus.OK);
    }
    @Operation(
        summary = "Xoa bình luận",
        description = "- Xoa bình luận"
    )
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<ApiResponse> DeleteComment(@PathVariable Long id) {
        return new ResponseEntity<>(interactionService.deleteComment(id) , HttpStatus.OK);
    }

    @Operation(
        summary = "get All Like bài viết",
        description = "get All Like bài viết"
    )
    @GetMapping("/like/getAll/{articleId}")
    public ResponseEntity<ResponseBase<List<LikeDto>>> createNewLike(@PathVariable Long articleId) {
        return new ResponseEntity<>(new ResponseBase<>(interactionService.getAllLikeByArticle(articleId)), HttpStatus.OK);
    }

    @Operation(
        summary = "get All Comment bài viết",
        description = "get AllComment bài viết"
    )
    @GetMapping("/comment/getAll/{articleId}")
    public ResponseEntity<ResponseBase<PageResponse<CommentDto>>> getAllComment(@PathVariable Long articleId, @ParameterObject Pageable pageable) {
        return new ResponseEntity<>(new ResponseBase<>(interactionService.getAllComment(articleId, pageable)), HttpStatus.OK);
    }

    @Operation(
        summary = "Kiểm tra xem user đã like bài viết đó chưa",
        description = "Kiểm tra xem user đã like bài viết đó chưa"
    )
    @GetMapping("/like/checkUser")
    public ResponseEntity<ApiResponse> checkLike(@Valid @ParameterObject CheckUserLikeRequest request) {
        return new ResponseEntity<>(interactionService.checkUserLike(request),HttpStatus.OK);
    }
}
