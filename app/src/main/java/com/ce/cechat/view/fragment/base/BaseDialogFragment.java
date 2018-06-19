package com.ce.cechat.view.fragment.base;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;

/**
 * @author CE Chen
 *
 * DialogFragment 的基类
 */

public class BaseDialogFragment extends AppCompatDialogFragment {

    private static final String TAG = "BaseDialogFragment";
    protected OnDialogFragmentInteractionListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onAttach");
        if (context instanceof OnDialogFragmentInteractionListener) {
            mListener = (OnDialogFragmentInteractionListener) context;
            Log.v(TAG, "onAttach mListener = " + mListener);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDialogFragmentInteractionListener");
        }
    }

    public boolean isShowing(){
        return getDialog() != null && getDialog().isShowing();
    }

    public void show(FragmentManager manager, String tag, boolean isResume){
        Log.v(TAG, "show mListener = " + mListener);
        if(!isShowing()){
            if(isResume){
                if(!isAdded()){
                    show(manager, tag);
                }else{
                    FragmentTransaction ft = manager.beginTransaction();
                    ft.show(this);
                    ft.commit();
                }
            } else {
                FragmentTransaction ft = manager.beginTransaction();
                if(!isAdded()){
                    ft.add(this, tag);
                }else{
                    ft.show(this);
                }
                ft.commitAllowingStateLoss();
            }
        }
    }

    public void dismiss(boolean isResume){
        if(isResume){
            dismiss();
        }else{
            dismissAllowingStateLoss();
        }
    }

    @Override
    public void dismiss() {
        if(isShowing()){
            super.dismiss();
        }
    }

    @Override
    public void dismissAllowingStateLoss() {
        if(isShowing()){
            super.dismissAllowingStateLoss();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "onDetach");
        mListener = null;
    }

    /**
     * On button pressed call back
     * @param uri uri
     */
    public void onButtonPressed(Uri uri) {
        Log.v(TAG, "onButtonPressed mListener = " + mListener);
        if (mListener != null) {
            mListener.onDialogFragmentInteraction(uri);
        }
    }

    public void onButtonPressed(Uri uri, Bundle bundle) {
        if (mListener != null) {
            mListener.onDialogFragmentInteraction(uri, bundle);
        }
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
    public interface OnDialogFragmentInteractionListener {

        /**
         * On fragment interaction call back
         * @param uri uri
         */
        void onDialogFragmentInteraction(Uri uri);

        /**
         * On fragment interaction call back
         * @param uri uri.
         * @param pBundle Bundle
         */
        void onDialogFragmentInteraction(Uri uri, Bundle pBundle);
    }

}
