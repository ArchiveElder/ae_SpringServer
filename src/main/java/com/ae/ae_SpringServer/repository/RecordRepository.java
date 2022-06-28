package com.ae.ae_SpringServer.repository;

import com.ae.ae_SpringServer.api.dto.DateAnalysisDto;
import com.ae.ae_SpringServer.domain.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecordRepository {
    private final EntityManager em;

    // 차후에 s3로 이미지 보내고 받은 url로 저장시키게
    public void save(Record record) { em.persist(record); }

    public List<Record> findUserRecord(Long id) {
        String param = id.toString();
        return em.createQuery("select r from Record r" + " join fetch r.user u where u.user_id = ?1", Record.class)
                .setParameter(1, param)
                .getResultList();
    }

    public List<Record> findDateRecords(Long id, String date) {
        return em.createQuery("select r from Record r join fetch r.user u where u.id = :param and r.date = :date", Record.class)
                .setParameter("param", id)
                .setParameter("date", date)
                .getResultList();
    }
    //날짜별로 그룹화하고 정렬후 현재 날짜는 제외하고 7개 날짜 검색하는 쿼리

//    public List analysisDates(Long id, int offset, int limit) {
//        //return em.createQuery("SELECT r.date FROM Record r JOIN FETCH r.user u WHERE u.id = :param GROUP BY r.date ORDER BY r.date DESC LIMIT 7", Record.class)
//        /*return em.createQuery("SELECT r.date FROM Record r JOIN r.user u WHERE u.id = :param GROUP BY r.date ORDER BY r.date DESC")
//                .setMaxResults(7)
//                .setParameter("param", id)
//                .getResultList();*/
//        return em.createQuery("SELECT r.date FROM Record r" +
//                        " JOIN r.user u WHERE u.id = :param" +
//                        " GROUP BY r.date" +
//                        " ORDER BY r.date DESC")
//                .setParameter("param" ,id)
//                .setFirstResult(offset)
//                .setMaxResults(limit)
//                .getResultList();
//    }
    public Page analysisDates(Long id, Pageable paging){
        Query query = em.createQuery("SELECT r.date, SUM(r.cal), SUM(r.carb), SUM(r.protein), SUM(r.fat)" +
                        " FROM Record r" +
                        " INNER JOIN r.user u WHERE u.id = :param" +
                        " GROUP BY r.date" +
                        " ORDER BY r.date DESC")
                        .setParameter("param", id);


        List<Object[]> resultList = query.getResultList();
        List<DateAnalysisDto> dateAnalysisDtos = new ArrayList<>();
        for(Object[] row : resultList){
            String date = (String)row[0];
            String sumCal = (String)row[1];
            String sumCarb = (String)row[2];
            String sumPro = (String)row[3];
            String sumFat = (String)row[4];
            dateAnalysisDtos.add(new DateAnalysisDto(date, sumCal, sumCarb, sumPro, sumFat));
        }
        return (Page)dateAnalysisDtos;
    }

    //가장 최근날짜와 가장 오래된 날짜를 변수에 저장하고, 다시 em으로 record 테이블 선택,
    //user 테이블과 join fetch하며 날짜가 오래된 날짜와 최근날짜 사이인(비교문) 식단을 검색하는 쿼리날리고,
    // 식단 리스트 값 반환
//    public List<Record> analysis(){
//
//    }

    public List<Record> findDetaileOne(Long id, Long record_id) {
        return em.createQuery("select r from Record r join r.user u where u.id = :param and r.id = :record_id", Record.class)
                .setParameter("param", id)
                .setParameter("record_id", record_id)
                .getResultList();

    }


    public List<Record> analysis(String frst, String lst) {
        return em.createQuery("SELECT r FROM Record r WHERE r.date <= ?1 and r.date >= ?2",Record.class)
                .setParameter(1,frst)
                .setParameter(2, lst)
                .getResultList();
    }

//    @Data
//    @AllArgsConstructor
//    private class DateAnalysisDto {
//        private String date;
//        private String sumCal;
//        private String sumCarb;
//        private String sumPro;
//        private String sumFat;
//    }
}
