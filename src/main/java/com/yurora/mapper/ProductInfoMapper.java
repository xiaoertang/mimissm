package com.yurora.mapper;

import com.yurora.pojo.ProductInfo;
import com.yurora.pojo.ProductInfoExample;
import com.yurora.pojo.vo.ProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductInfoMapper {
    int countByExample(ProductInfoExample example);

    int deleteByExample(ProductInfoExample example);

    int deleteByPrimaryKey(Integer pId);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    List<ProductInfo> selectByExample(ProductInfoExample example);

    ProductInfo selectByPrimaryKey(Integer pId);

    int updateByExampleSelective(@Param("record") ProductInfo record, @Param("example") ProductInfoExample example);

    int updateByExample(@Param("record") ProductInfo record, @Param("example") ProductInfoExample example);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);

    /**
     * 实现多条件查询
     */
    List<ProductInfo> selectConditionSplitPage(ProductVo vo);

    /**
     * 批量删除
     */
    int deleteBatch(String[] pids);
}