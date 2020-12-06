/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/6
 * @Time: 18:07
 */
package com.qun.util;

import com.qun.service.PermissionService;
import com.qun.service.PermissionServiceImpl;

public class TestUtil {
    public void test() {
        PermissionServiceImpl bean = SpringUtil.getBean(PermissionServiceImpl.class);

    }
}
