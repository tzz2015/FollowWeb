package com.example.follow.service;

import com.example.follow.model.Spread;

import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/16
 **/
public interface SpreadService {
    Spread updateSpread(Spread spread);

    void deleteSpread(long id);

    List<Spread> findAll();
}
