package com.example.goldenticketnew.service.branch;


import com.example.goldenticketnew.dtos.BranchDto;
import com.example.goldenticketnew.enums.ResponseCode;
import com.example.goldenticketnew.exception.InternalException;
import com.example.goldenticketnew.model.Branch;
import com.example.goldenticketnew.payload.response.BranchResponse;
import com.example.goldenticketnew.payload.response.PageResponse;
import com.example.goldenticketnew.payload.resquest.GetAllBranchRequest;
import com.example.goldenticketnew.repository.IBranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BranchService implements IBranchService {

    @Autowired
    private IBranchRepository branchRepository;

    @Override
    public List<BranchDto> getBranchesThatShowTheMovie(Integer movieId) {
        List<Branch> branches = branchRepository.getBranchThatShowTheMovie(movieId);
        return branches.stream().map(BranchDto::new).collect(Collectors.toList());
    }
    @Override
    public PageResponse<BranchDto> getAllBranch(GetAllBranchRequest request) {
        Page<Branch> branchPage = branchRepository.findAll(request.getSpecification(), request.getPageable());
        return new PageResponse<>(branchPage.map(BranchDto::new));
    }

    @Override
    public List<BranchResponse> getListBranch(GetAllBranchRequest request) {
        List<Branch> branches= branchRepository.findAll(request.getSpecification());
        return branches.stream().map(BranchResponse::new).collect(Collectors.toList());
    }

    @Override
    public BranchResponse getBranch(Integer id) {
        Branch branch = branchRepository.findById(id).orElseThrow(() -> new InternalException(ResponseCode.BRANCH_NOT_FOUND));
        return new BranchResponse(branch);
    }
}
