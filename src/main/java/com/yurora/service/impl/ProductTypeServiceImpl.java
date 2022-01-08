package com.yurora.service.impl;

import com.yurora.mapper.ProductTypeMapper;
import com.yurora.pojo.ProductType;
import com.yurora.pojo.ProductTypeExample;
import com.yurora.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 唐孝顺
 * @date 2022/1/8 16:27
 */
@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Override
    public List<ProductType> getAll() {
        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}
