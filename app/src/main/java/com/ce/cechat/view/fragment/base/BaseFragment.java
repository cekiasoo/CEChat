package com.ce.cechat.view.fragment.base;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author CE Chen
 *
 * Fragment 的基类
 */
public abstract class BaseFragment extends Fragment {

    protected OnFragmentInteractionListener mListener;

    Unbinder unbinder;

    @NonNull
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        setListener();
    }

    /**
     * 初始化 View
     */
    protected abstract void initView(View view);

    /**
     * 设置 Listener
     */
    protected abstract void setListener();

    /**
     * 获取 layout id
     * @return layout id
     */
    protected abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onButtonPressed(Uri uri, Bundle bundle) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri, bundle);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDialogFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        /**
         *
         * @param uri
         */
        void onFragmentInteraction(Uri uri);

        /**
         *
         * @param uri
         * @param bundle
         */
        void onFragmentInteraction(Uri uri, Bundle bundle);
    }
}
