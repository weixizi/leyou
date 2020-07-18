package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 根据参数查询分页
     * @param key
     * @param page
     * @param row
     * @param sortBy
     * @param desc
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "key" , required = false) String key,
            @RequestParam(value = "page" , defaultValue = "1")Integer page,
            @RequestParam(value = "row", defaultValue = "5") Integer row,
            @RequestParam(value = "sortBy" , required = false) String sortBy,
            @RequestParam(value = "desc" , required = false) Boolean desc
    ){
        PageResult<Brand> result = brandService.queryBrandByPage(key,page,row,sortBy,desc);

        //判断结果和结果集是否为空，成立择返回404
        if (CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("deletebyid")
    public ResponseEntity<Void> deleteById(@RequestParam("id") String id){
        brandService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    public  ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        brandService.saveBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
