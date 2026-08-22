package com.example.goldenticketnew.service.branch;



import com.example.goldenticketnew.dtos.BranchDto;
import com.example.goldenticketnew.payload.response.BranchResponse;
import com.example.goldenticketnew.payload.response.PageResponse;
import com.example.goldenticketnew.payload.resquest.BranchRequest;
import com.example.goldenticketnew.payload.resquest.GetAllBranchRequest;

import java.util.List;

public interface IBranchService {
    List<BranchDto> getBranchesThatShowTheMovie(Integer movieId);
    PageResponse<BranchDto> getAllBranch(GetAllBranchRequest request);
    List<BranchResponse> getListBranch(GetAllBranchRequest request);

    BranchResponse getBranch(Integer id);
    BranchResponse createBranch(BranchRequest request);
    BranchResponse updateBranch(Integer id, BranchRequest request);
    List<String> getAllCities();
    List<BranchResponse> getBranchesByCity(String city);
}
