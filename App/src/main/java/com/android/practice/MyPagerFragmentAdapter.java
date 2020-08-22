package com.android.practice;

import android.content.Intent;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * TODO
 *
 * @author dev.liang <a href="mailto:dev.liang@outlook.com">Contact me.</a>
 * @version 2.0.0
 * @since 2020/08/22 11:04
 */
class MyPagerFragmentAdapter extends FragmentStatePagerAdapter {

    private final FragmentManager mFragmentManager;
    private List<String> mDataList;

    private int mRefreshInterval, mPageSize;

    public MyPagerFragmentAdapter(FragmentManager fm, List<String> dataList) {
        super(fm);
        mFragmentManager = fm;
        mDataList = dataList;
    }


    @Override
    public Fragment getItem(int position) {
//        if (CollectionUtils.isEmpty(mDatas)) {
//            return null;
//        }
//        MarketInitModel.RowEntity data = mDatas.get(position);
//        switch (data.getType()) {
//            case AppConstant.TYPE_VALUE:
//                return MarketValueListFragment.newInstance(data, mRefreshInterval, mPageSize);
//            case AppConstant.TYPE_INCREASE:
//                return MarketIncreaseListFragment.newInstance(data, mRefreshInterval, mPageSize);
//            default:
//                return MarketListFragment.newInstance(data, mRefreshInterval);
//        }
        return CircleTopListFragment.newInstance("");
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    @Override
    public int getCount() {
        return CollectionUtils.isEmpty(mDataList) ? 0 : mDataList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mDataList == null) {
            return null;
        }
//        MarketInitModel.RowEntity data = mDatas.get(position);
//        return data.getName();
        return mDataList.get(position);
    }

    public void updatePages(List<String> datas) {
        mDataList = datas;
    }


}

