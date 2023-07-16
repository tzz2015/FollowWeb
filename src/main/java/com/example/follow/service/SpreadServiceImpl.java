package com.example.follow.service;

import com.example.follow.except.BusinessException;
import com.example.follow.model.Spread;
import com.example.follow.repository.SpreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/16
 **/
@Service
public class SpreadServiceImpl implements SpreadService {
    @Autowired
    private SpreadRepository spreadRepository;

    @Override
    public Spread updateSpread(Spread spread) {
        if (spread.getId() != null) {
            Spread first = spreadRepository.findFirstById(spread.getId());
            if (first == null) {
                throw new BusinessException("该条数据不存在");
            }
        }
        return spreadRepository.save(spread);
    }

    @Override
    public void deleteSpread(long id) {
        Spread spread = spreadRepository.findFirstById(id);
        if (spread == null) {
            throw new BusinessException("该条数据不存在");
        }
        spreadRepository.deleteById(id);
    }

    @Override
    public List<Spread> findAll() {
        return spreadRepository.findAll();
    }
}
