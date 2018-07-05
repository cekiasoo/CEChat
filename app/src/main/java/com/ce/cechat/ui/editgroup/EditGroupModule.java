package com.ce.cechat.ui.editgroup;

import com.ce.cechat.ui.groupdetail.GroupDetailBiz;
import com.ce.cechat.ui.groupdetail.IGroupDetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
@Module
public class EditGroupModule {


    @Provides
    public IEditGroupContract.IEditGroupMemberBiz provideEditGroupMemberBiz() {
        return new EditGroupMemberBiz();
    }

    @Provides
    public IGroupDetailContract.IGroupDetailBiz provideGroupDetailBiz() {
        return new GroupDetailBiz();
    }

}
