package com.banerjee.sirsha.imageeditor.fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.banerjee.sirsha.imageeditor.activity.BaseActivity;

/**
 * Created by Sirsha Banerjee on 18/1/18.
 */

public abstract class BaseFragment extends Fragment  {

    protected final String TAG = this.getClass().getSimpleName();

    /**
     * Initialize the components for Fragment's view
     *
     * @param view A View inflated into Fragment
     */
    protected abstract void initializeComponent(View view);//to initialize the fragments components

    /**
     * Returns the resource id of the layout which will be used for setContentView() for the Activity
     *
     * @return resource id of the xml layout
     */
    protected abstract int defineLayoutResource();

    /**
     * Contains last clicked time
     */
    private long lastClickedTime = 0;

    /**
     * Shows progress indication in screens
     */
    protected ProgressBar pbProgress;

    /**
     * EmptyView Layout
     */
    private LinearLayout llEmptyView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(defineLayoutResource(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeComponent(view);
    }



    /**
     * Adds the Fragment into layout container.
     *
     * @param container               Resource id of the layout in which Fragment will be added
     * @param currentFragment         Current loaded Fragment to be hide
     * @param nextFragment            New Fragment to be loaded into container
     * @param requiredAnimation       true if screen transition animation is required
     * @param commitAllowingStateLoss true if commitAllowingStateLoss is needed
     * @return true if new Fragment added successfully into container, false otherwise
     * @throws ClassCastException    Throws exception if getActivity() is not an instance of BaseActivity
     * @throws IllegalStateException Exception if Fragment transaction is invalid
     */
    protected boolean addFragment(final int container, final android.app.Fragment currentFragment, final android.app.Fragment nextFragment, final boolean requiredAnimation, final boolean commitAllowingStateLoss) throws ClassCastException, IllegalStateException {
        if (getActivity() != null) {
            if (getActivity() instanceof BaseActivity) {
                return ((BaseActivity) getActivity()).addFragment(container, currentFragment, nextFragment,  commitAllowingStateLoss);
            } else {
                throw new ClassCastException(BaseActivity.class.getName() + " can not be cast into " + getActivity().getClass().getName());
            }
        }
        return false;
    }

    /**
     * Replaces the Fragment into layout container.
     *
     * @param container               Resource id of the layout in which Fragment will be added
     * @param fragmentManager         Activity fragment manager
     * @param nextFragment            New Fragment to be loaded into container
     * @param requiredAnimation       true if screen transition animation is required
     * @param commitAllowingStateLoss true if commitAllowingStateLoss is needed
     * @return true if new Fragment added successfully into container, false otherwise
     * @throws ClassCastException    Throws exception if getActivity() is not an instance of BaseActivity
     * @throws IllegalStateException Exception if Fragment transaction is invalid
     */
    protected boolean replaceFragment(final int container, final FragmentManager fragmentManager, final android.app.Fragment nextFragment, final boolean requiredAnimation, final boolean commitAllowingStateLoss) throws ClassCastException, IllegalStateException {
        if (getActivity() != null) {
            if (getActivity() instanceof BaseActivity) {
                return ((BaseActivity) getActivity()).replaceFragment(container, fragmentManager, nextFragment,  commitAllowingStateLoss);
            } else {
                throw new ClassCastException(BaseActivity.class.getName() + " can not be cast into " + getActivity().getClass().getName());
            }
        }
        return false;
    }

}
