package com.yurora.test;

import com.yurora.utils.MD5Util;
import org.junit.Test;

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
}
