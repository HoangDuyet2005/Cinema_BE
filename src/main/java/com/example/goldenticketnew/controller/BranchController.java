package com.example.goldenticketnew.controller;


import com.example.goldenticketnew.dtos.BranchDto;
import com.example.goldenticketnew.payload.response.BranchResponse;
import com.example.goldenticketnew.payload.response.PageResponse;
import com.example.goldenticketnew.payload.response.ResponseBase;
import com.example.goldenticketnew.payload.resquest.GetAllBranchRequest;
import com.example.goldenticketnew.repository.IBranchRepository;
import com.example.goldenticketnew.service.branch.IBranchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value="/api/branches", produces = "application/json")
@Tag(name = "Branch Controller", description = "Thao tác với các chi nhánh")
public class BranchController {
    @Autowired
    private IBranchService branchService;



    @GetMapping
    public ResponseEntity<ResponseBase<List<BranchDto>>> getBranchesThatShowTheMovie(@RequestParam Integer movieId) {
        return ResponseEntity.ok(new ResponseBase<>(branchService.getBranchesThatShowTheMovie(movieId)));
    }
    @Operation(
        summary = "Get All Branch với filter  paging ",
        description = "- Get All Branch với filter paging"
    )
    @GetMapping("/getAll")
    public ResponseEntity<ResponseBase<PageResponse<BranchDto>>> getAllBranch(@ParameterObject Pageable pageable , @ParameterObject GetAllBranchRequest request) {
        request.setPageable(pageable);
        return new ResponseEntity<>(new ResponseBase<>(branchService.getAllBranch(request)), HttpStatus.OK);
    }
    @Operation(
        summary = "Get All Branch với filter ",
        description = "- Get All Branch với filter"
    )
    @GetMapping("/getList")
    public ResponseEntity<ResponseBase<List<BranchResponse>>> getAllBranch(@ParameterObject GetAllBranchRequest request) {
        return new ResponseEntity<>(new ResponseBase<>(branchService.getListBranch(request)), HttpStatus.OK);
    }
    @Operation(
        summary = "Get Detail Branch  ",
        description = "- Get All Branch "
    )
    @GetMapping("/getDetail")
    public ResponseEntity<ResponseBase<BranchResponse>> getBranchDetail( @Parameter Integer id) {
        return new ResponseEntity<>(new ResponseBase<>(branchService.getBranch(id)), HttpStatus.OK);
    }
}


