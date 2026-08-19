package com.example.goldenticketnew.payload.resquest;

import com.example.goldenticketnew.model.Movie;
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
public class GetAllMovieRequest {
    @JsonIgnore
    private Pageable pageable;

    private String actors;

    private String categories;

    private String director;

    private Integer isShowing;
    private String name;

    public Specification<Movie> getSpecification(){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(cb.like(cb.lower(root.get(Movie.Fields.name)), "%" + name.toLowerCase() + "%"));
            }
            if (categories != null) {
                predicates.add(cb.like(cb.lower(root.get(Movie.Fields.categories)), "%" + categories.toLowerCase() + "%"));
            }
            if (director != null) {
                predicates.add(cb.like(cb.lower(root.get(Movie.Fields.director)), "%" + director.toLowerCase() + "%"));
            }
            if (actors != null) {
                predicates.add(cb.like(cb.lower(root.get(Movie.Fields.actors)), "%" + actors.toLowerCase() + "%"));
            }

            if (isShowing != null) {
                predicates.add(cb.equal(root.get(Movie.Fields.isShowing),isShowing));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
