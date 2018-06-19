package com.ce.cechat.model.biz;

import com.ce.cechat.model.bean.User;
import com.hyphenate.chat.EMGroupManager;

import java.util.List;

/**
 * @author CE Chen
 *
 * 选择联系人业务逻辑操作接口
 */
public interface ISelectContactBiz {

    /**
     * 获取所有联系人
     * @return 所有联系人
     */
    List<User> getAllContact();

    /**
     * 获取群 style
     * @param style style
     * @return 群 style
     */
    EMGroupManager.EMGroupStyle getGroupStyle(int style);


}
