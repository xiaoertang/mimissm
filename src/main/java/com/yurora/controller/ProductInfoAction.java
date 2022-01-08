package com.yurora.controller;

import com.github.pagehelper.PageInfo;
import com.mysql.cj.Session;
import com.yurora.pojo.ProductInfo;
import com.yurora.service.ProductInfoService;
import com.yurora.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author 唐孝顺
 * @date 2022/1/8 13:18
 */
@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    public static final  int PAGE_SIZE = 5;
    public String saveFileName = "";//异步上传存文件名，增加和更新用这个文件名
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

    //ajax文件上传
    @RequestMapping("/ajaxImg")
    @ResponseBody
    public Object ajaxImg(MultipartFile pimage, HttpServletRequest request){
        //提取生成文件名UUID+上传图片的后缀.jpg .png
        saveFileName = FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());

        try {
            //得到项目中图片存放的路径
            String path = request.getServletContext().getRealPath("/image_big");
            //转存
            pimage.transferTo(new File(path+File.separator+saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回客户端JSON对象，封装图片的路径，为了页面实现立即回显
        JSONObject object = new JSONObject();
        object.put("imgurl",saveFileName);

        return object.toString();

    }

    //商品添加
    @RequestMapping("/save")
    public String Save(ProductInfo info,HttpServletRequest request){
        info.setpImage(saveFileName);
        info.setpDate(new Date());
        //info对象中有表单提交上来的5个数据，有异步ajax上来的图片名称数据和上架时间数据
        int num = -1;
        try {
            num = productInfoService.save(info);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(num > 0){
            request.setAttribute("msg","添加成功！");
        }else{
            request.setAttribute("msg","添加失败！");
        }
        //增加成功后，重新访问数据库，跳转到分页显示的action上

        return "forward:/prod/split.action";
    }
}
