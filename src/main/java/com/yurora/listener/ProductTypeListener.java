package com.yurora.listener;

import com.yurora.pojo.ProductType;
import com.yurora.service.ProductTypeService;
import com.yurora.service.impl.ProductTypeServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * @author 唐孝顺
 * @date 2022/1/8 16:34
 */
@WebListener
public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //手工从Spring容器中获取ProductTypeServiceImpl的对象
        ApplicationContext context = new ClassPathXmlApplicationContext("application_*.xml");
        ProductTypeService productTypeService = (ProductTypeService) context.getBean("ProductTypeServiceImpl");
        List<ProductType> typeList = productTypeService.getAll();
        //放在全局应用作用域中，供新增页面，修改页面，前台的查询功能提供全部商品类别集合
        servletContextEvent.getServletContext().setAttribute("typeList",typeList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
