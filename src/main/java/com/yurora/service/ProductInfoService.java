package com.yurora.service;

import com.github.pagehelper.PageInfo;
import com.yurora.pojo.ProductInfo;

import java.util.List;

/**
 * @author 唐孝顺
 * @date 2022/1/8 13:15
 */
@SuppressWarnings({"all"})
public interface ProductInfoService {
    //显示全部商品信息，不分页
    List<ProductInfo>  getAll();

    //分页显示
    PageInfo splitPage(int pageNum, int pageSize);
}
