package com.ce.cechat.ui.selectcontact;

import com.ce.cechat.ui.creategroup.CreateGroupBiz;
import com.ce.cechat.ui.creategroup.ICreateGroupContract;
import com.ce.cechat.ui.groupdetail.GroupDetailBiz;
import com.ce.cechat.ui.groupdetail.IGroupDetailContract;
import com.ce.cechat.ui.newmsg.IInviteContract;
import com.ce.cechat.ui.newmsg.InviteBiz;

import dagger.Module;
import dagger.Provides;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
@Module
public class SelectContactModule {

    @Provides
    ICreateGroupContract.ICreateGroupBiz createGroupBiz() {
        return new CreateGroupBiz();
    }

    @Provides
    IInviteContract.IInviteBiz inviteBiz() {
        return new InviteBiz();
    }

    @Provides
    IGroupDetailContract.IGroupDetailBiz groupDetailBiz() {
        return new GroupDetailBiz();
    }

}
