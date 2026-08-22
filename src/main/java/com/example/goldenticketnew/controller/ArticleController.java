package com.example.goldenticketnew.controller;

import com.example.goldenticketnew.dtos.ArticleDto;
import com.example.goldenticketnew.dtos.ArticleReportDto;
import com.example.goldenticketnew.dtos.CategoryDto;
import com.example.goldenticketnew.enums.ArticleStatus;
import com.example.goldenticketnew.enums.ArticleType;
import com.example.goldenticketnew.exception.BadRequestException;
import com.example.goldenticketnew.model.Category;
import com.example.goldenticketnew.payload.article.request.*;
import com.example.goldenticketnew.payload.response.PageResponse;
import com.example.goldenticketnew.payload.response.ResponseBase;
import com.example.goldenticketnew.repository.ICategoryRepository;
import com.example.goldenticketnew.security.CurrentUser;
import com.example.goldenticketnew.security.UserPrincipal;
import com.example.goldenticketnew.service.article.IArticleService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
@Tag(name = "Article Controller", description = "Thao tác với các bài đăng")
public class ArticleController {
    private final IArticleService articleService;
    private final ICategoryRepository categoryRepository;

    @Operation(
        summary = "Thêm mới Review của User ",
        description = "- Thêm mới Review của User"
    )
    @PostMapping("/addNewReview")
    public ResponseEntity<ResponseBase<ArticleDto>> addNewArticleReview(@Valid @RequestBody AddNewReviewRequest request) {
        request.setType(ArticleType.REVIEWS);
        return ResponseEntity.ok(new ResponseBase<>(articleService.addNewArticleReview(request)));
    }
    @Operation(
        summary = "Thêm mới tin tức, sự kiện khuyến mãi của Staff",
        description = "- Thêm mới tin tức, sự kiện khuyến mãi của Staff"
    )
    @PostMapping("/addNew")
    public ResponseEntity<ResponseBase<ArticleDto>> addNewArticleNew(@Valid @RequestBody AddNewArticleRequest request) {
        request.setType(ArticleType.NEWS);
        return ResponseEntity.ok(new ResponseBase<>(articleService.addNewArticle(request)));
    }
    @Operation(
        summary = "Chỉnh sửa Article ",
        description = "- Chỉnh sửa Article"
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseBase<ArticleDto>> updateArticle( @RequestBody UpdateArticleRequest request) {
        return ResponseEntity.ok(new ResponseBase<>(articleService.updateArticle(request)));
    }
    @Operation(
        summary = "Chỉnh sửa trạng thái của Article ",
        description = "- Chỉnh sửa trạng thái của Article"
    )
    @PutMapping("/changeStatus")
    public ResponseEntity<ResponseBase<ArticleDto>> changeStatusArticle(@Valid @ParameterObject ChangeArticleStatusRequest request) {
            return ResponseEntity.ok(new ResponseBase<>(articleService.changeStatusArticle(request)));
    }
    @Operation(
        summary = "Get All Article với filter ",
        description = "- Get All Article với filter"
    )
    @GetMapping("/getAll")
    public ResponseEntity<ResponseBase<List<ArticleDto>>> findAllArticle(@ParameterObject GetAllArticleRequest request) {
        return ResponseEntity.ok(new ResponseBase<>(articleService.getAllArticle(request)));
    }   @Operation(
        summary = "Get Detail Article ",
        description = "- Get Detail Article "
    )
    @GetMapping("/getDetail")
    public ResponseEntity<ResponseBase<ArticleDto>> getDetail(@Parameter Long id ) {
        return ResponseEntity.ok(new ResponseBase<>(articleService.getDetailArticle(id)));
    }
  @Operation(
    summary = "Get Detail Article by ( slug) ",
    description = "- Get Detail Article by (slug) "
)
@GetMapping("/getDetail/{slug}")
public ResponseEntity<ResponseBase<ArticleDto>> getDetailByTitle(@PathVariable String slug ) {
    return ResponseEntity.ok(new ResponseBase<>(articleService.getArticleBySLug(slug)));
}


    @Operation(
        summary = "Get All Article với filter và paging ",
        description = "- Get All Article với filter và paging"
    )
    @GetMapping("/getAllPaging")
    public ResponseBase<PageResponse<ArticleDto>> findAllArticlePaging(@ParameterObject Pageable pageable, @ParameterObject GetAllArticleRequest request) {
        request.setPageable(pageable);
        return new ResponseBase<>(articleService.getAllArticlePaging(request));
    }

    @Operation(
        summary = "Get All Category For User",
        description = "Get All Category For User"
    )
    @GetMapping("/user/getAll")
    public ResponseEntity<ResponseBase<List<ArticleDto>>> getAllCateByUser(@CurrentUser UserPrincipal currentUser, @Parameter ArticleStatus status) {
        return ResponseEntity.ok(new ResponseBase<>(articleService.getAllByUser(currentUser, status)));
    }
    @Operation(
        summary = "Get Report user and article",
        description = "Get Report user and article"
    )
    @GetMapping("/user/getAllReport")
    public ResponseEntity<ResponseBase<ArticleReportDto>> getReportUser(@Parameter String dateTime) {
        return ResponseEntity.ok(new ResponseBase<>(articleService.getReport(dateTime)));
    }

    @Operation(
        summary = "Thêm bài viết lưu trữ ( yêu thích) của user",
        description = "- Thêm bài viết lưu trữ ( yêu thích) của user"
    )
    @PostMapping("/user/addSaveArticle")
    public ResponseEntity<ResponseBase<List<ArticleDto>>> addNewArticleinUser(@Valid @Parameter Long userId, @Parameter Long articleId) {
        return ResponseEntity.ok(new ResponseBase<>(articleService.addNewArticleInuser(userId,articleId)));
    }
    @Operation(
        summary = "Gỡ bài viết lưu trữ ( yêu thích) của user",
        description = "- Gỡ bài viết lưu trữ ( yêu thích) của user"
    )
    @PostMapping("/user/removeSaveArticle")
    public ResponseEntity<ResponseBase<List<ArticleDto>>> removeArticleinUser(@Valid @Parameter Long userId, @Parameter Long articleId) {
        return ResponseEntity.ok(new ResponseBase<>(articleService.removeArticleInuser(userId,articleId)));
    }

    @Operation(
        summary = "Get All Category lưu trữ( yêu thích của user)",
        description = "Get All Category lưu trữ( yêu thích của user)"
    )
    @GetMapping("/user/saveArticle/getAll")
    public ResponseEntity<ResponseBase<PageResponse<ArticleDto>>> getAllCateByUser(@Parameter Long userId, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(new ResponseBase<>(articleService.getAllArticlePagingInUser(userId,pageable)));
    }

    @Operation(
        summary = "Kiểm tra user đã lưu bài viết chưa",
        description = "- Trả về true/false"
    )
    @GetMapping("/user/checkSaveArticle")
    public ResponseEntity<ResponseBase<Boolean>> checkSaveArticle(@Parameter Long userId, @Parameter Long articleId) {
        return ResponseEntity.ok(new ResponseBase<>(articleService.checkSaveArticle(userId, articleId)));
    }

    @Operation(
        summary = "Get All Category",
        description = "- Get all article categories for admin"
    )
    @GetMapping("/category/getAll")
    public ResponseEntity<ResponseBase<List<CategoryDto>>> getAllCategory() {
        List<CategoryDto> categories = categoryRepository.findAll()
            .stream()
            .map(CategoryDto::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(new ResponseBase<>(categories));
    }

    @Operation(
        summary = "Add Category",
        description = "- Add article category for admin"
    )
    @PostMapping("/category/add")
    public ResponseEntity<ResponseBase<CategoryDto>> addCategory(@RequestBody JsonNode request) {
        String name = extractCategoryName(request);
        if (name == null || name.trim().isEmpty()) {
            throw new BadRequestException("Category name must not be empty");
        }
        name = name.trim();
        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new BadRequestException("Category name already exists");
        }
        Category category = new Category();
        category.setName(name);
        return new ResponseEntity<>(new ResponseBase<>(new CategoryDto(categoryRepository.save(category))), HttpStatus.CREATED);
    }

    private String extractCategoryName(JsonNode request) {
        if (request == null || request.isNull()) {
            return null;
        }
        if (request.isTextual()) {
            return request.asText();
        }
        if (request.hasNonNull("name")) {
            return request.get("name").asText();
        }
        if (request.hasNonNull("categoryName")) {
            return request.get("categoryName").asText();
        }
        return null;
    }
}
