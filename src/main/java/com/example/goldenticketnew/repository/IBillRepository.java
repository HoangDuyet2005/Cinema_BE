package com.example.goldenticketnew.repository;


import com.example.goldenticketnew.dtos.DayTransactionReport;
import com.example.goldenticketnew.dtos.TopMovieReportDto;
import com.example.goldenticketnew.dtos.TranSuccess;
import com.example.goldenticketnew.dtos.UserReportDto;
import com.example.goldenticketnew.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;

public interface IBillRepository extends JpaRepository<Bill, Integer> , JpaSpecificationExecutor<Bill> {
    Optional<Bill> findByBookingCode(String bookingCode);
    @Query(value = "SELECT a.dateTran,a.transactionCount , b.ticketAmount, a.incomeAmount FROM\n" +
        "               (\n" +
        "                SELECT DATE_FORMAT(bill.created_time,'%Y-%m-%d') dateTran,count(bill.id) transactionCount , sum(bill.price) incomeAmount\n" +
        "        FROM bill \n" +
        "        where bill.status = 1 and DATE_FORMAT(bill.created_time,'%Y-%m-%d') between :fromDate and :toDate\n" +
        "        group by DATE_FORMAT(bill.created_time,'%Y-%m-%d')\n" +
        "        ) a \n" +
        "        ,(\n" +
        "        SELECT DATE_FORMAT(bill.created_time,'%Y-%m-%d') dateTran, count(ticket.id) ticketAmount\n" +
        "        FROM bill \n" +
        "        INNER JOIN ticket  ON bill.id = bill_id\n" +
        "        where bill.status = 1 and DATE_FORMAT(bill.created_time,'%Y-%m-%d') between :fromDate and :toDate\n" +
        "        group by DATE_FORMAT(bill.created_time,'%Y-%m-%d')\n" +
        "        ) b  Where  a.dateTran = b.dateTran",nativeQuery = true)
    List<DayTransactionReport> findDashBoardBillByFromDateToDateAndStatusSuccess(@Param("fromDate") String fromDate , @Param("toDate") String toDate);


    @Query(value = "SELECT DATE_FORMAT(bill.created_time,'%Y-%m-%d') dateTran,count(bill.id) transactionCount , 0 ticketAmount, sum(bill.price) incomeAmount\n" +
        "FROM bill \n" +
        "where bill.status = 2 and DATE_FORMAT(bill.created_time,'%Y-%m-%d') between :fromDate and :toDate " +
        "group by DATE_FORMAT(bill.created_time,'%Y-%m-%d')",nativeQuery = true)
    List<DayTransactionReport> findDashBoardBillByFromDateToDateAndStatusEx(@Param("fromDate") String fromDate , @Param("toDate") String toDate);

    @Query(value = "SELECT x.dateTran dateTran, COUNT(x.id) transactionCount, SUM(x.ticketAmount) ticketAmount, SUM(x.price) incomeAmount FROM (" +
        " SELECT b.id id, DATE_FORMAT(b.created_time,'%Y-%m-%d') dateTran, b.price price, COUNT(t.id) ticketAmount" +
        " FROM bill b" +
        " LEFT JOIN ticket t ON b.id = t.bill_id" +
        " LEFT JOIN schedule s ON t.schedule_id = s.id" +
        " WHERE b.status = :status" +
        " AND DATE_FORMAT(b.created_time,'%Y-%m-%d') BETWEEN :fromDate AND :toDate" +
        " AND (:branchId = 0 OR s.branch_id = :branchId)" +
        " AND (:movieId = 0 OR s.movie_id = :movieId)" +
        " GROUP BY b.id, DATE_FORMAT(b.created_time,'%Y-%m-%d'), b.price" +
        ") x GROUP BY x.dateTran ORDER BY x.dateTran", nativeQuery = true)
    List<DayTransactionReport> findDashboardBillByFilters(@Param("fromDate") String fromDate,
                                                          @Param("toDate") String toDate,
                                                          @Param("status") Integer status,
                                                          @Param("branchId") Integer branchId,
                                                          @Param("movieId") Integer movieId);

    @Query(value = "SELECT m.id id, m.name name, COUNT(t.id) ticketAmount, SUM(s.price) incomeAmount" +
        " FROM bill b" +
        " INNER JOIN ticket t ON b.id = t.bill_id" +
        " INNER JOIN schedule s ON t.schedule_id = s.id" +
        " INNER JOIN movie m ON s.movie_id = m.id" +
        " WHERE b.status = :status" +
        " AND DATE_FORMAT(b.created_time,'%Y-%m-%d') BETWEEN :fromDate AND :toDate" +
        " AND (:branchId = 0 OR s.branch_id = :branchId)" +
        " AND (:movieId = 0 OR s.movie_id = :movieId)" +
        " GROUP BY m.id, m.name" +
        " ORDER BY incomeAmount DESC LIMIT 5", nativeQuery = true)
    List<TopMovieReportDto> findTopMoviesByFilters(@Param("fromDate") String fromDate,
                                                   @Param("toDate") String toDate,
                                                   @Param("status") Integer status,
                                                   @Param("branchId") Integer branchId,
                                                   @Param("movieId") Integer movieId);

    @Query(value = "SELECT CASE WHEN COALESCE(SUM(capacity), 0) = 0 THEN 0 ELSE ROUND(SUM(ticketAmount) * 100 / SUM(capacity), 2) END" +
        " FROM (" +
        " SELECT s.id scheduleId, COUNT(t.id) ticketAmount, MAX(r.capacity) capacity" +
        " FROM bill b" +
        " INNER JOIN ticket t ON b.id = t.bill_id" +
        " INNER JOIN schedule s ON t.schedule_id = s.id" +
        " INNER JOIN room r ON s.room_id = r.id" +
        " WHERE b.status = :status" +
        " AND DATE_FORMAT(b.created_time,'%Y-%m-%d') BETWEEN :fromDate AND :toDate" +
        " AND (:branchId = 0 OR s.branch_id = :branchId)" +
        " AND (:movieId = 0 OR s.movie_id = :movieId)" +
        " GROUP BY s.id" +
        ") x", nativeQuery = true)
    Double findAverageOccupancyRateByFilters(@Param("fromDate") String fromDate,
                                             @Param("toDate") String toDate,
                                             @Param("status") Integer status,
                                             @Param("branchId") Integer branchId,
                                             @Param("movieId") Integer movieId);

    @Query(value = "SELECT u.id id, u.name name, u.username username, u.email email, u.image image," +
        " COUNT(x.id) transactionCount, SUM(x.ticketAmount) ticketAmount, SUM(x.price) incomeAmount FROM (" +
        " SELECT b.id id, b.user_id userId, b.price price, COUNT(t.id) ticketAmount" +
        " FROM bill b" +
        " LEFT JOIN ticket t ON b.id = t.bill_id" +
        " LEFT JOIN schedule s ON t.schedule_id = s.id" +
        " WHERE b.status = :status" +
        " AND (:fromDate IS NULL OR DATE_FORMAT(b.created_time,'%Y-%m-%d') >= :fromDate)" +
        " AND (:toDate IS NULL OR DATE_FORMAT(b.created_time,'%Y-%m-%d') <= :toDate)" +
        " AND (:branchId = 0 OR s.branch_id = :branchId)" +
        " AND (:movieId = 0 OR s.movie_id = :movieId)" +
        " AND (:userId = 0 OR b.user_id = :userId)" +
        " GROUP BY b.id, b.user_id, b.price" +
        ") x INNER JOIN user u ON x.userId = u.id" +
        " GROUP BY u.id, u.name, u.username, u.email, u.image" +
        " ORDER BY incomeAmount DESC", nativeQuery = true)
    List<UserReportDto> findUserDashboardByFilters(@Param("fromDate") String fromDate,
                                                   @Param("toDate") String toDate,
                                                   @Param("status") Integer status,
                                                   @Param("branchId") Integer branchId,
                                                   @Param("movieId") Integer movieId,
                                                   @Param("userId") Long userId);
    @Query(value = "SELECT  u.id id, u.name name,u.username username,u.email email, u.image image, count(b.id) transactionCount , 0 ticketAmount, sum(b.price) incomeAmount\n" +
        "        FROM bill b\n" +
        "        INNER JOIN user u ON b.user_id = u.id\n" +
        "        where b.status = 2 \n" +
        "        group by user_id",nativeQuery = true)
    List<UserReportDto> findAllByStatusExGroupByUser();

    @Query(value = "SELECT a.id , a.name ,a.username ,a.email , a.image ,a.transactionCount , a.incomeAmount, b.ticketAmount FROM\n" +
        "(SELECT  u.id id, u.name name,u.username username,u.email email, u.image image,count(b.id) transactionCount , sum(b.price) incomeAmount\n" +
        "               FROM bill b\n" +
        "                INNER JOIN user u ON b.user_id = u.id\n" +
        "                where b.status = 1 \n" +
        "                group by user_id\n" +
        ")  a               \n" +
        " , (              \n" +
        "SELECT  u.id id , count(ticket.id) ticketAmount\n" +
        "               FROM bill b\n" +
        "       INNER JOIN ticket  ON b.id = bill_id\n" +
        "                INNER JOIN user u ON b.user_id = u.id\n" +
        "                where b.status = 1 \n" +
        "                group by user_id\n" +
        "                )  b Where a.id = b.id\n" +
        "                ",nativeQuery = true)
    List<UserReportDto> findAllByStatusSuccessGroupByUser();
    @Query(value = "select a.user_id as userId,a.amountTran, (a.amountTran/(a.amountTran + b.amountTran)) as precentAmount  from\n" +
        "(Select count(id) as amountTran ,user_id  from bill  where year(created_at) = ?1 and month(created_at) = ?2 and status = 1 group by user_id) a ,\n" +
        "(Select count(id) as amountTran  ,user_id  from bill  where year(created_at) = ?1 and month(created_at) = ?2 and status = 2 group by user_id) b ",nativeQuery = true)
    List<TranSuccess> findAllByUserTransucess(Integer year,Integer month);
}
