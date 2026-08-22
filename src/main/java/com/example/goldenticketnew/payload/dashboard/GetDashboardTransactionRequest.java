package com.example.goldenticketnew.payload.dashboard;

import com.example.goldenticketnew.enums.BillStatus;
import com.example.goldenticketnew.model.Bill;
import com.example.goldenticketnew.model.Schedule;
import com.example.goldenticketnew.model.Ticket;
import com.example.goldenticketnew.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetDashboardTransactionRequest {

    private String fromDate;

    private String toDate;

    private BillStatus status;

    private Long userId;

    private Integer branchId;

    private Integer movieId;

    public Specification<Bill> getSpecification(){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (status != null) {
                predicates.add(cb.equal(root.get(Bill.Fields.status),status));
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (fromDate != null) {
                LocalDateTime fromDateTime = LocalDateTime.of(LocalDate.parse(fromDate, formatter) , LocalTime.MIN);
                predicates.add(cb.greaterThanOrEqualTo(root.get(Bill.Fields.createdTime), fromDateTime));
            }
            if(toDate != null){
                LocalDateTime toDateTime = LocalDateTime.of(LocalDate.parse(toDate, formatter) , LocalTime.MAX);
                predicates.add(cb.lessThanOrEqualTo(root.get(Bill.Fields.createdTime), toDateTime));
            }
            if (userId != null) {
                predicates.add(cb.equal(root.get(Bill.Fields.user).get(User.Fields.id),userId));
            }
            Join<Ticket, Schedule> scheduleJoin = null;
            if ((branchId != null && branchId > 0) || (movieId != null && movieId > 0)) {
                query.distinct(true);
                Join<Bill, Ticket> ticketJoin = root.join("tickets", JoinType.INNER);
                scheduleJoin = ticketJoin.join("schedule", JoinType.INNER);
            }
            if (branchId != null && branchId > 0) {
                predicates.add(cb.equal(scheduleJoin.get(Schedule.Fields.branch).get("id"), branchId));
            }
            if (movieId != null && movieId > 0) {
                predicates.add(cb.equal(scheduleJoin.get(Schedule.Fields.movie).get("id"), movieId));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
