package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;

import java.util.List;

public interface BrandService {
    PageResult<Brand> queryBrandByPage(String key, Integer page, Integer row, String sortBy, Boolean desc);

    void deleteById(String id);


    void saveBrand(Brand brand, List<Long> cids);

}
