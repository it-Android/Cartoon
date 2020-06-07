package com.jq.cartoon1.fragment;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.jq.cartoon1.R;

/**
 * 主界面碎片布局管理器
 *
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/23 17:49
 **/
public class MainFragmentManager {
    private FragmentManager manager;//碎片布局管理器
    private HomeFragment home;//首页
    private SearchFragment search;//搜索页
    private CollectFragment collect;//收藏页
    private MineFragment mine;//我的页面

    public MainFragmentManager(FragmentManager manager) {
        this.manager = manager;
        home = new HomeFragment();
        search = new SearchFragment();
        collect = new CollectFragment();
        mine = new MineFragment();
        manager.beginTransaction()
                .add(R.id.main_frame, home)//添加首页
                .commit();//提交
    }

    //切换界面
    public Fragment replace(int param) {
        if (param < 0) param = 0;
        if (param > 3) param = 3;//防止错误
        Fragment fragment = null;
        switch (param) {
            case 0:
                fragment = home;
                break;
            case 1:
                fragment = search;
                break;
            case 2:
                fragment = collect;
                break;
            case 3:
                fragment = mine;
                break;
        }
        if (manager != null) {
            manager.beginTransaction()
                    .replace(R.id.main_frame, fragment)//replace切换
                    .commit();//切换页面
        }
        return fragment;//返回切换到的碎片界面
    }

    public void onRestart() {
        if (home != null&&home.getView()!=null)
            home.onRestart();
        if (search != null&&search.getView()!=null)
            search.onRestart();
        if (collect != null&&collect.getView()!=null)
            collect.onRestart();
        if (mine != null&&mine.getView()!=null)
            mine.onRestart();
    }

}
