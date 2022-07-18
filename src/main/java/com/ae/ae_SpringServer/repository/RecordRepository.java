package com.ae.ae_SpringServer.repository;

import com.ae.ae_SpringServer.domain.Record;
import com.ae.ae_SpringServer.jpql.DateAnalysisDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecordRepository {
    private final EntityManager em;

    public void save(Record record) { em.persist(record); }

    public List<Record> findDateRecords(Long id, String date) {
        return em.createQuery("select r from Record r join fetch r.user u where u.id = :param and r.date = :date", Record.class)
                .setParameter("param", id)
                .setParameter("date", date)
                .getResultList();
    }

    public List<Record> findDetaileOne(Long id, Long record_id) {
        return em.createQuery("select r from Record r join r.user u where u.id = :param and r.id = :record_id", Record.class)
                .setParameter("param", id)
                .setParameter("record_id", record_id)
                .getResultList();

    }

    //7개의 기록된 날짜의 날짜별 총열량, 총영양소
    public List<DateAnalysisDto> analysisDate(Long id) {
        String sql = "SELECT r.record_date, SUM(r.cal), SUM(r.carb), SUM(r.protein), SUM(r.fat)" +
                " FROM record r JOIN user u WHERE r.user_user_id = :user_id" +
                " and r.record_date != :date" +
                " GROUP BY r.record_date ORDER BY r.record_date DESC LIMIT 7";
        //NativeQuery로 직접 날림
        Query nativeQuery = em.createNativeQuery(sql)
                .setParameter("user_id", id)
                .setParameter("date", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd.")));
        List<Object[]> resultList = nativeQuery.getResultList();
        List<DateAnalysisDto> dateAnalysisDtos = new ArrayList<>();
        for(Object[] row : resultList){
            String date = (String)row[0];
            Double sumCal = (Double)row[1];
            Double sumCarb = (Double)row[2];
            Double sumPro = (Double)row[3];
            Double sumFat = (Double)row[4];
            dateAnalysisDtos.add(new DateAnalysisDto(date, sumCal, sumCarb, sumPro, sumFat));
        }
        return dateAnalysisDtos;
    }








}
