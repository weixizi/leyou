package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据pid查询分类
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoryByPid(@RequestParam("pid") Long pid){

        // 判断pid是否为空或小于0 成立返回错误请求400
        if (pid == null || pid < 0){
            return ResponseEntity.badRequest().build();
        };

        List<Category> categoryList = categoryService.queryCategoryByPid(pid);

        //判断结果集为空 成立返回错误请求404
        if (CollectionUtils.isEmpty(categoryList)){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(categoryList);
        }
    }


}
