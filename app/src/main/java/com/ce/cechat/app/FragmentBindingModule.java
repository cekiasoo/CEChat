package com.ce.cechat.app;

import com.ce.cechat.di.BaseFragmentComponent;
import com.ce.cechat.di.ContactFragmentComponent;
import com.ce.cechat.di.FragmentScoped;
import com.ce.cechat.di.GroupListFragmentComponent;
import com.ce.cechat.ui.addcontact.SearchFragment;
import com.ce.cechat.ui.addcontact.SearchModule;
import com.ce.cechat.ui.contactlist.ContactFragment;
import com.ce.cechat.ui.contactlist.ContactModule;
import com.ce.cechat.ui.creategroup.CreateGroupFragment;
import com.ce.cechat.ui.creategroup.CreateGroupModule;
import com.ce.cechat.ui.dynamic.SettingFragment;
import com.ce.cechat.ui.editgroup.EditGroupFragment;
import com.ce.cechat.ui.editgroup.EditGroupModule;
import com.ce.cechat.ui.groupdetail.GroupDetailFragment;
import com.ce.cechat.ui.groupdetail.GroupDetailModule;
import com.ce.cechat.ui.detail.ContactDetailFragment;
import com.ce.cechat.ui.detail.DetailModule;
import com.ce.cechat.ui.grouplist.GroupListFragment;
import com.ce.cechat.ui.grouplist.GroupListModule;
import com.ce.cechat.ui.login.LoginFragment;
import com.ce.cechat.ui.login.LoginModule;
import com.ce.cechat.ui.login.SignUpFragment;
import com.ce.cechat.ui.login.SignUpModule;
import com.ce.cechat.ui.newmsg.InviteFragment;
import com.ce.cechat.ui.newmsg.InviteModule;
import com.ce.cechat.ui.selectcontact.SelectContactFragment;
import com.ce.cechat.ui.selectcontact.SelectContactModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
@Module(subcomponents = {
        BaseFragmentComponent.class,
        ContactFragmentComponent.class,
        GroupListFragmentComponent.class})
public abstract class FragmentBindingModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginFragment loginFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = SignUpModule.class)
    abstract SignUpFragment signUpFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = DetailModule.class)
    abstract ContactDetailFragment contactDetailFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = GroupDetailModule.class)
    abstract GroupDetailFragment groupDetailFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = EditGroupModule.class)
    abstract EditGroupFragment editGroupFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = ContactModule.class)
    abstract ContactFragment contactFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = CreateGroupModule.class)
    abstract CreateGroupFragment createGroupFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = GroupListModule.class)
    abstract GroupListFragment groupListFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = SelectContactModule.class)
    abstract SelectContactFragment selectContactFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = InviteModule.class)
    abstract InviteFragment inviteFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = SearchModule.class)
    abstract SearchFragment searchFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract SettingFragment settingFragment();



}
