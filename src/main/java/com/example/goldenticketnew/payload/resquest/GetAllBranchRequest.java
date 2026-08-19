package com.example.goldenticketnew.payload.resquest;

import com.example.goldenticketnew.model.Branch;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAllBranchRequest {
    @JsonIgnore
    private Pageable pageable;
    private String name;
    private String address;

    public Specification<Branch> getSpecification(){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(cb.like(cb.lower(root.get(Branch.Fields.name)), "%" + name.toLowerCase() + "%"));
            }
            if (address != null) {
                predicates.add(cb.like(cb.lower(root.get(Branch.Fields.address)), "%" + address.toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
