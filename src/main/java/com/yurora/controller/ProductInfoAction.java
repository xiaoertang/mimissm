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
import org.springframework.ui.Model;
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
    public static final int PAGE_SIZE = 5;
    /**
     * 异步上传存文件名，增加和更新用这个文件名
     */
    public String saveFileName = "";
    @Autowired
    private ProductInfoService productInfoService;

    /**
     * 显示商品不分页
     */
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request) {
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list", list);
        return "product";
    }

    //显示第一页的商品信息
    @RequestMapping("/split")
    public String split(HttpServletRequest request) {
        //得到第1页的数据
        PageInfo info = productInfoService.splitPage(1, PAGE_SIZE);
        request.setAttribute("info", info);
        return "product";
    }

    /**
     * ajax分页处理
     *
     * @param page
     * @param session
     */
    @RequestMapping("/ajaxSplit")
    @ResponseBody
    public void ajaxSplit(int page, HttpSession session) {
        //取得当前page参数的页面的数据
        PageInfo info = productInfoService.splitPage(page, PAGE_SIZE);
        session.setAttribute("info", info);
    }

    /**
     * ajax文件上传
     *
     * @param pimage
     * @param request
     * @return
     */
    @RequestMapping("/ajaxImg")
    @ResponseBody
    public Object ajaxImg(MultipartFile pimage, HttpServletRequest request) {
        //提取生成文件名UUID+上传图片的后缀.jpg .png
        saveFileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());

        try {
            //得到项目中图片存放的路径
            String path = request.getServletContext().getRealPath("/image_big");
            //转存
            pimage.transferTo(new File(path + File.separator + saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回客户端JSON对象，封装图片的路径，为了页面实现立即回显
        JSONObject object = new JSONObject();
        object.put("imgurl", saveFileName);

        return object.toString();

    }

    /**
     * 商品添加
     */
    @RequestMapping("/save")
    public String Save(ProductInfo info, HttpServletRequest request) {
        info.setpImage(saveFileName);
        info.setpDate(new Date());
        //info对象中有表单提交上来的5个数据，有异步ajax上来的图片名称数据和上架时间数据
        int num = -1;
        try {
            num = productInfoService.save(info);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (num > 0) {
            request.setAttribute("msg", "添加成功！");
        } else {
            request.setAttribute("msg", "添加失败！");
        }

        //清空saveFileName变量中的内容，为下次增加或修改的异步Ajax的上传处理
        saveFileName = "";

        //增加成功后，重新访问数据库，跳转到分页显示的action上
        return "forward:/prod/split.action";
    }

    /**
     * 编辑单个商品信息
     *
     * @return
     */
    @RequestMapping("/one")
    public String one(int pid, Model model) {
        ProductInfo info = productInfoService.getById(pid);
        model.addAttribute("prod", info);
        return "update";
    }

    /**
     * 更新提交商品信息
     */
    @RequestMapping("/update")
    public String update(ProductInfo info, HttpServletRequest request) {
        //ajax的异步上传图片上传，如果有图片上传过，则saveFileName里上传上来的图片的名称
        //如果没有使用异步上传过图片，则saveFileName="";
        //实体类info使用隐藏表单域提供上来的pImage原始图片名称
        if (!saveFileName.equals("")) {
            info.setpImage(saveFileName);
        }
        //完成更新处理
        int num = -1;
        try {
            num = productInfoService.update(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0) {
            //此时说明更新成功
            request.setAttribute("msg", "更新成功！");
        } else {
            //更新失败
            request.setAttribute("msg", "更新失败！");
        }
        saveFileName = "";
        return "forward:/prod/split.action";
    }

    @RequestMapping("/delete")
    public String delete(int pid,HttpServletRequest request){
        int num = -1;
        try {
            num = productInfoService.delete(pid);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(num > 0){
            request.setAttribute("msg","删除成功！");
        }else{
            request.setAttribute("msg","删除失败！");
        }
        //删除结束后跳转到分页显示
        return "forward:/prod/deleteAjaxSplit.action";
    }

    @RequestMapping(value = "/deleteAjaxSplit",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public Object deleteAjaxSplit(HttpServletRequest request){
        //取得第一页的数据
        PageInfo info = productInfoService.splitPage(1,PAGE_SIZE);
        request.getSession().setAttribute("info",info);
        return request.getAttribute("msg");
    }

    /**
     * 批量删除商品
     * @return
     */
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids,HttpServletRequest request){
        //将请求的字符串分解，形成一个商品id字符数组
        String []pid = pids.split(",");
        int num = -1;
        num = productInfoService.deleteBatch(pid);
        try {
            if(num > 0){
                request.setAttribute("msg","批量删除成功！");
            }else{
                request.setAttribute("msg","批量删除失败！");
            }
        }catch (Exception e){
            request.setAttribute("msg","商品不可删除！");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }
}
