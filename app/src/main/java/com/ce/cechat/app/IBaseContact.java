package com.ce.cechat.app;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
public interface IBaseContact {

    /**
     * @author CE Chen
     * <p>
     * 作用 :
     */
    public interface IBasePresenter<T extends IBaseView> {

        /**
         * 在 Resume 时 View 和 Presenter 绑定
         * @param pView View
         */
        void takeView(T pView);

        /**
         * View 和 Presenter 分离
         */
        void dropView();

    }

    /**
     * @author CE Chen
     * <p>
     * 作用 :
     */
    public interface IBaseView<T> {

        /**
         * 是否是活动的
         * @return 是返回true 否返回false
         */
        boolean isActive();

        /**
         * View 和 Presenter 绑定
         */
        void takeView();

        /**
         * View 和 Presenter 分离
         */
        void dropView();

    }


    public interface IBaseBiz {

    }

}
