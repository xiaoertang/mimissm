package com.yurora.test;

import com.yurora.mapper.AdminMapper;
import com.yurora.pojo.Admin;
import com.yurora.pojo.AdminExample;
import com.yurora.service.AdminService;
import com.yurora.service.impl.AdminServiceImpl;
import com.yurora.utils.MD5Util;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 唐孝顺
 * @date 2022/1/3 22:57
 */
public class MyTest {
    @Test
    public void testMD5(){
        //测试密文
        String miwen = MD5Util.getMD5("123456789");
        System.out.println(miwen.length());
    }

    @Autowired
    AdminMapper adminMapper;
    @Test
    public void testAdmin(){
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andANameEqualTo("admin");
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        System.out.println(admins);
    }
}
