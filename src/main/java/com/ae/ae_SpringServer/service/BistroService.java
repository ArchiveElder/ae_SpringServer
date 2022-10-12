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

    public BistroV2 findOne(Long id) { return bistroRepository.findOne(id); }

    public List<BistroV2> getMiddle(String wide) {
        return bistroRepository.getMiddle(wide);
    }

    public List<BistroV2> getCategoryList(String wide, String middle) {
        return bistroRepository.getCategoryList(wide, middle);
    }

    public List<BistroV2> getCategories(String wide, String middle) {
        return bistroRepository.getCategories(wide, middle);
    }

    public List<BistroV2> getBistro() {
        return bistroRepository.getBistro();
    }


}
