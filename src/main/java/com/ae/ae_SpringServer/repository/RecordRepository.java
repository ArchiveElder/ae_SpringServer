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

    /* 2차때 구현
    public List<Record> findUserRecordSome(Long id) {
        String param = id.toString();
        return em.createQuery("select top 6 r from record r" + " join fetch r.user u where u.user_id = ?1 order by r.server_date desc", Record.class)
                .setParameter(1, param)
                .getResultList();
    }
     */

}
