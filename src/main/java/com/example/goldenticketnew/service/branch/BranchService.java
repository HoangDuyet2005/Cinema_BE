package com.example.goldenticketnew.service.branch;


import com.example.goldenticketnew.dtos.BranchDto;
import com.example.goldenticketnew.enums.ResponseCode;
import com.example.goldenticketnew.exception.InternalException;
import com.example.goldenticketnew.model.Branch;
import com.example.goldenticketnew.payload.response.BranchResponse;
import com.example.goldenticketnew.payload.response.PageResponse;
import com.example.goldenticketnew.payload.resquest.BranchRequest;
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

    @Override
    public BranchResponse createBranch(BranchRequest request) {
        Branch branch = new Branch();
        applyBranchRequest(branch, request);
        return new BranchResponse(branchRepository.save(branch));
    }

    @Override
    public BranchResponse updateBranch(Integer id, BranchRequest request) {
        Branch branch = branchRepository.findById(id).orElseThrow(() -> new InternalException(ResponseCode.BRANCH_NOT_FOUND));
        applyBranchRequest(branch, request);
        return new BranchResponse(branchRepository.save(branch));
    }

    @Override
    public List<String> getAllCities() {
        return branchRepository.findDistinctCities();
    }

    @Override
    public List<BranchResponse> getBranchesByCity(String city) {
        List<Branch> branches;
        if (city == null || city.trim().isEmpty() || city.equalsIgnoreCase("ALL")) {
            branches = branchRepository.findAll();
        } else {
            branches = branchRepository.findByCityIgnoreCase(city.trim());
        }
        return branches.stream().map(BranchResponse::new).collect(Collectors.toList());
    }

    private void applyBranchRequest(Branch branch, BranchRequest request) {
        branch.setName(request.getName());
        branch.setCity(request.getCity());
        branch.setAddress(request.getAddress());
        branch.setPhoneNo(request.getPhoneNo());
        branch.setImgURL(request.getImgURL());
    }
}
