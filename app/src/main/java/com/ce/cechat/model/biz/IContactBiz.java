package com.ce.cechat.model.biz;

import com.ce.cechat.model.bean.User;
import com.hyphenate.easeui.domain.EMGroup;

import java.util.List;
import java.util.Map;

/**
 * @author CE Chen
 *
 * 联系人业务逻辑操作接口
 */
public interface IContactBiz {

    /**
     * 是否有添加好友的红点
     * @return 是返回true 否返回false
     */
    public boolean haveInviteRedDot();


    /**
     * 删除红点记录
     */
    public void clearInviteRedDot();

    /**
     * 获取所有联系人
     *
     * @param pFromServer 是否从网络获取
     * @param pContactListener IContactListener
     */
    public void getContacts(boolean pFromServer, IContactListener pContactListener);

    /**
     * List 转成 Map
     * @param pAllContacts List
     * @return Map<String, EMGroup>
     */
    public Map<String, EMGroup> list2UserMap(List<User> pAllContacts);
}
