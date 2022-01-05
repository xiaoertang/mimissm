package com.yurora.service;

import com.yurora.pojo.Admin;

/**
 * @author 唐孝顺
 * @date 2022/1/4 14:44
 */
public interface AdminService {
    Admin login(String name, String pwd);
}
