package com.ae.ae_SpringServer.repository;

import com.ae.ae_SpringServer.domain.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecordRepository {
    private final EntityManager em;

    // 차후에 s3로 이미지 보내고 받은 url로 저장시키게
    public void save(Record record) { em.persist(record); }

    public Record findOne(Long id) { return em.find(Record.class, id); }

    public List<Record> findAll() {
        return em.createQuery("select r from Record r", Record.class)
                .getResultList();
    }

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
    //가장 최근날짜와 가장 오래된 날짜를 변수에 저장하고, 다시 em으로 record 테이블 선택,
    //user 테이블과 join fetch하며 날짜가 오래된 날짜와 최근날짜 사이인(비교문) 식단을 검색하는 쿼리날리고,
    // 식단 리스트 값 반환
    public List<Record> analysis(Long id) {
        return em.createQuery("select r from Record r join fetch r.user u where u.id = :param", Record.class)
                .setParameter("param", id)
                .getResultList();
    }

    /* 2차때 구현
    public List<Record> findUserRecordSome(Long id) {
        String param = id.toString();
        return em.createQuery("select top 6 r from record r" + " join fetch r.user u where u.user_id = ?1 order by r.server_date desc", Record.class)
                .setParameter(1, param)
                .getResultList();
    }
     */

}
