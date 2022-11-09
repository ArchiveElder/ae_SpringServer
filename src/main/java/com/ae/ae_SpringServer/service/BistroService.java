package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.domain.Bistro;
import com.ae.ae_SpringServer.domain.BistroV2;
import com.ae.ae_SpringServer.repository.BistroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BistroService {
    private final BistroRepository bistroRepository;
    /* v2로 변경되면서 도메인 변경으로 인한 코드 */

    public BistroV2 findOneV2(Long id) { return bistroRepository.findOne(id); }

    public Bistro findV1One(Long id) { return bistroRepository.findV1One(id); }

    public List<BistroV2> getMiddleV2(String wide) {
        return bistroRepository.getMiddleV2(wide);
    }

    public List<BistroV2> getCategoryListV2(String wide, String middle) {
        return bistroRepository.getCategoryListV2(wide, middle);
    }

    public List<BistroV2> getCategoriesV2(String wide, String middle) {
        return bistroRepository.getCategoriesV2(wide, middle);
    }
    /* v1 사용자들을 위한 코드 */
    public List<Bistro> getMiddle(String wide) {
        return bistroRepository.getMiddle(wide);
    }


    public List<BistroV2> getBistroV2() {
        return bistroRepository.getBistroV2();
    }
    public List<Bistro> getCategoryList(String wide, String middle) {
        return bistroRepository.getCategoryList(wide, middle);
    }

    public List<Bistro> getCategories(String wide, String middle) {
        return bistroRepository.getCategories(wide, middle);
    }

    public List<Bistro> getBistro() {
        return bistroRepository.getBistro();
    }

    public List<BistroV2> getCategoryMain(String mainCategory) {
        return bistroRepository.getCategoryMain(mainCategory);
    }

    public List<BistroV2> getCategoryMiddle(String mainCategory, String middleCategory) {
        return bistroRepository.getCategoryMiddle(mainCategory, middleCategory);
    }

    public List<BistroV2> getBistroMain(String siteWide, String siteMiddle, String mainCategory) {
        return bistroRepository.getBistroMain(siteWide, siteMiddle, mainCategory);
    }

    public List<BistroV2> getBistroMiddle(String siteWide, String siteMiddle, String mainCategory, String middleCategory) {
        return bistroRepository.getBistroMiddle(siteWide, siteMiddle, mainCategory, middleCategory);
    }

    public List<BistroV2> getSiteWideMain(String siteWide, String mainCategory) {
        return bistroRepository.getSiteWideMain(siteWide, mainCategory);
    }
}
