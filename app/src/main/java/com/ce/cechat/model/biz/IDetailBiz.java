package com.ce.cechat.model.biz;

/**
 * @author CE Chen
 *
 * 详细资料业务逻辑操作接口
 */
public interface IDetailBiz {

    /**
     * 删除联系人
     *
     * @param pHyphenateId HyphenateId
     * @param pDeleteContactListener DeleteContactListener
     */
    public void deleteContact(String pHyphenateId, DeleteContactListener pDeleteContactListener);


}
