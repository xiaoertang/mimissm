package com.yurora.mapper;

import com.yurora.pojo.ProductInfo;
import com.yurora.pojo.ProductType;
import com.yurora.pojo.ProductTypeExample;
import com.yurora.pojo.vo.ProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductTypeMapper {
    int countByExample(ProductTypeExample example);

    int deleteByExample(ProductTypeExample example);

    int deleteByPrimaryKey(Integer typeId);

    int insert(ProductType record);

    int insertSelective(ProductType record);

    List<ProductType> selectByExample(ProductTypeExample example);

    ProductType selectByPrimaryKey(Integer typeId);

    int updateByExampleSelective(@Param("record") ProductType record, @Param("example") ProductTypeExample example);

    int updateByExample(@Param("record") ProductType record, @Param("example") ProductTypeExample example);

    int updateByPrimaryKeySelective(ProductType record);

    int updateByPrimaryKey(ProductType record);

    //实现多条件查询
    public List<ProductInfo> selectConditionSplitPage(ProductVo vo);

    //批量删除
    public int deleteBatch(String[] pids);
}