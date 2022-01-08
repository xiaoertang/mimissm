package com.yurora.controller;

import com.github.pagehelper.PageInfo;
import com.mysql.cj.Session;
import com.yurora.pojo.ProductInfo;
import com.yurora.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 唐孝顺
 * @date 2022/1/8 13:18
 */
@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    public static final  int PAGE_SIZE = 5;

    @Autowired
    private ProductInfoService productInfoService;
    //显示商品不分页
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list",list);
        return "product";
    }

    //显示第一页的商品信息
    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        //得到第1页的数据
        PageInfo info = productInfoService.splitPage(1,PAGE_SIZE);
        request.setAttribute("info",info);
        return "product";
    }

    //ajax分页处理
    @RequestMapping("/ajaxSplit")
    @ResponseBody
    public void ajaxSplit(int page, HttpSession session){
        //取得当前page参数的页面的数据
        PageInfo info = productInfoService.splitPage(page,PAGE_SIZE );
        session.setAttribute("info",info);
    }
}
