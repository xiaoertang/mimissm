package com.yurora.service.impl;

import com.yurora.mapper.AdminMapper;
import com.yurora.pojo.Admin;
import com.yurora.pojo.AdminExample;
import com.yurora.service.AdminService;
import com.yurora.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 唐孝顺
 * @date 2022/1/4 14:45
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {
        //根据传入的用户或密码到DB中查询相应的用户
        //如果有条件，则一定创建AdminExample的对象，用来封装条件

        AdminExample example = new AdminExample();
        //添加用户名a_name条件
        example.createCriteria().andANameEqualTo(name);

        List<Admin> list = adminMapper.selectByExample(example);
        if (list.size() > 0 ) {
            Admin admin = list.get(0);
            //如果查询到用户，则进行密码比对，注意密码是密文
            /**
             * 分析：
             * admin.getPass==>密文
             * pwd==>000000
             * 在进行密码比对，要将传过来的pwd进行加密，在与数据库中的密码进行比对
             */
            String miPwd = MD5Util.getMD5(pwd);
            if (miPwd.equals(admin.getaPass())) {
                return admin;
            }
        }
        return null;
    }
}
