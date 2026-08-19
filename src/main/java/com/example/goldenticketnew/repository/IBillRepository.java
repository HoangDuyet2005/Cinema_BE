package com.example.goldenticketnew.repository;


import com.example.goldenticketnew.dtos.DayTransactionReport;
import com.example.goldenticketnew.dtos.TranSuccess;
import com.example.goldenticketnew.dtos.TransactionReportSuccess;
import com.example.goldenticketnew.dtos.UserReportDto;
import com.example.goldenticketnew.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IBillRepository extends JpaRepository<Bill, Integer> , JpaSpecificationExecutor<Bill> {
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