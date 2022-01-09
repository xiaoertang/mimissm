package com.yurora.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yurora.mapper.ProductInfoMapper;
import com.yurora.pojo.ProductInfo;
import com.yurora.pojo.ProductInfoExample;
import com.yurora.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 唐孝顺
 * @date 2022/1/8 13:16
 */

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAll() {
        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    //分页显示
    //分页公式：select * from 表名 limit 起始记录=((当前页数-1)*每页的条数),每页取几条
    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        //使用分页插件PageHelper工具类完成分页功能
        PageHelper.startPage(pageNum, pageSize);
        //进行PageInfo的数据封装
        //进行有条件的查询操作，必须创建ProductInfoExample对象
        ProductInfoExample example = new ProductInfoExample();
        //设置排序，按主键降序排序
        //select * from product_info order by p_id desc
        //拼接sql语句
        example.setOrderByClause("p_id desc");
        //设置完成排序后，取集合，在取集合之前完成PageHelper设置
        List<ProductInfo> list = productInfoMapper.selectByExample(example);
        //将查到的集合封装进PageHelper对象中
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {

        return productInfoMapper.insert(info);
    }

    @Override
    public ProductInfo getById(int pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }
}
