package com.ce.cechat.ui.selectcontact;

import com.ce.cechat.bean.User;
import com.ce.cechat.data.biz.DbBiz;
import com.hyphenate.chat.EMGroupManager;

import java.util.List;

import javax.inject.Inject;

/**
 * @author CE Chen
 *
 * 选择联系人业务逻辑操作类
 */
public class SelectContactBiz implements ISelectContactContract.ISelectContactBiz {

    @Inject
    public SelectContactBiz() {
    }

    @Override
    public List<User> getAllContact() {
        return DbBiz.newInstance().getDbManager().getContactDao().getContacts();
    }

    @Override
    public EMGroupManager.EMGroupStyle getGroupStyle(int style) {
        return int2GroupStyle(style);
    }

    /**
     * 将 int 转为 EMGroupStyle
     * @param pStyle 要转的 int 型 EMGroupStyle
     * @return EMGroupStyle
     */
    private EMGroupManager.EMGroupStyle int2GroupStyle(int pStyle) {

        for (EMGroupManager.EMGroupStyle style : EMGroupManager.EMGroupStyle.values()) {
            if (style.ordinal() == pStyle) {
                return style;
            }
        }

        return EMGroupManager.EMGroupStyle.EMGroupStylePublicJoinNeedApproval;
    }

}
