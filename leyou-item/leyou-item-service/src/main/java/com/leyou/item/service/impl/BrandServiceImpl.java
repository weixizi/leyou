package com.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Transactional
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;


    /**
     * 分页查询品牌
     *
     * @param key
     * @param page
     * @param row
     * @param sortBy
     * @param desc
     * @return
     */
    @Override
    public PageResult<Brand> queryBrandByPage(String key, Integer page, Integer row, String sortBy, Boolean desc) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        //模糊查询品牌关键字
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }

        //添加分页助手
        PageHelper.startPage(page, row);

        //添加排序
        if (StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + (desc ? " desc" : " asc"));
        }

        //查询
        List<Brand> brands = brandMapper.selectByExample(example);
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 根据id删除品牌
     * @param id
     */
    @Override
    public void deleteById(String id) {
        int i = brandMapper.deleteByPrimaryKey(id);
        int j = brandMapper.deleteCategoryAndBrand(id);
        if (i == 0  || j==0) throw new RuntimeException("删除异常");
    }


    /**
     * 新增品牌
     * @param brand
     * @param cids
     */
    @Override
    public void saveBrand(Brand brand, List<Long> cids) {
        brandMapper.insert(brand);
        Long bid = brand.getId();
        cids.forEach(cid->brandMapper.saveCategoryAndBrand(cid,bid));

    }
}
