package com.ae.ae_SpringServer.repository;

import com.ae.ae_SpringServer.domain.Bistro;
import com.ae.ae_SpringServer.domain.BistroV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BistroRepository {
    private final EntityManager em;

    public BistroV2 findOne(Long id) { return em.find(BistroV2.class, id);  }

    public Bistro findV1One(Long id) { return em.find(Bistro.class, id); }

    public List<BistroV2> getMiddleV2(String wide) {
        return em.createQuery("select b from BistroV2 b where b.wide = :wide group by b.middle", BistroV2.class)
                .setParameter("wide", wide)
                .getResultList();
    }

    public List<BistroV2> getCategoryListV2(String wide, String middle) {
        return em.createQuery("select b from BistroV2 b where b.wide = :wide and b.middle = :middle", BistroV2.class)
                .setParameter("wide", wide)
                .setParameter("middle", middle)
                .getResultList();
    }

    public List<BistroV2> getCategoriesV2(String wide, String middle) {
        return em.createQuery("select b from BistroV2 b where b.wide = :wide and b.middle = :middle group by b.category", BistroV2.class)
                .setParameter("wide", wide)
                .setParameter("middle", middle)
                .getResultList();
    }

    public List<BistroV2> getBistroV2() {
        return em.createQuery("select b from BistroV2 b", BistroV2.class)
                .getResultList();
    }
    public List<Bistro> getMiddle(String wide) {
        return em.createQuery("select b from Bistro b where b.wide = :wide group by b.middle", Bistro.class)
                .setParameter("wide", wide)
                .getResultList();
    }

    public List<Bistro> getCategoryList(String wide, String middle) {
        return em.createQuery("select b from Bistro b where b.wide = :wide and b.middle = :middle", Bistro.class)
                .setParameter("wide", wide)
                .setParameter("middle", middle)
                .getResultList();
    }

    public List<Bistro> getCategories(String wide, String middle) {
        return em.createQuery("select b from Bistro b where b.wide = :wide and b.middle = :middle group by b.category", Bistro.class)
                .setParameter("wide", wide)
                .setParameter("middle", middle)
                .getResultList();
    }

    public List<Bistro> getBistro() {
        return em.createQuery("select b from Bistro b", Bistro.class)
                .getResultList();
    }

    public List<BistroV2> getCategoryMain(String mainCategory) {
        return em.createQuery("select b from BistroV2 b where b.mainCategory = :mainCategory", BistroV2.class)
                .setParameter("mainCategory", mainCategory)
                .getResultList();
    }

    public List<BistroV2> getCategoryMiddle(String mainCategory, String middleCategory) {
        return em.createQuery("select b from BistroV2 b where b.mainCategory = :mainCategory and b.middleCategory = :middleCategory", BistroV2.class)
                .setParameter("mainCategory", mainCategory)
                .setParameter("middleCategory", middleCategory)
                .getResultList();
    }

    public List<BistroV2> getBistroMain(String siteWide, String siteMiddle, String mainCategory) {
        return em.createQuery("select b from BistroV2 b where b.wide = :siteWide and b.middle = :siteMiddle and b.mainCategory = :mainCategory", BistroV2.class)
                .setParameter("siteWide", siteWide)
                .setParameter("siteMiddle", siteMiddle)
                .setParameter("mainCategory", mainCategory)
                .getResultList();
    }

    public List<BistroV2> getBistroMiddle(String siteWide, String siteMiddle, String mainCategory, String middleCategory) {
        return em.createQuery("select b from BistroV2 b where b.wide = :siteWide and b.middle = :siteMiddle and b.mainCategory = :mainCategory and b.middleCategory = :middleCategory", BistroV2.class)
                .setParameter("siteWide", siteWide)
                .setParameter("siteMiddle", siteMiddle)
                .setParameter("mainCategory", mainCategory)
                .setParameter("middleCategory", middleCategory)
                .getResultList();
    }

    public List<BistroV2> getSiteWideMain(String siteWide, String mainCategory) {
        return em.createQuery("select b from BistroV2 b where b.wide = :siteWide and b.mainCategory = :mainCategory", BistroV2.class)
                .setParameter("siteWide", siteWide)
                .setParameter("mainCategory", mainCategory)
                .getResultList();
    }
}
