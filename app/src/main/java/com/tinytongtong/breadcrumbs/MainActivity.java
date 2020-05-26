package com.tinytongtong.breadcrumbs;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private BreadCrumbsView breadCrumbsView;

    LinkedList<Fragment> fragments = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        breadCrumbsView = findViewById(R.id.breadCrumbs);
        breadCrumbsView.setOnTabListener(new BreadCrumbsView.OnTabListener() {
            @Override
            public void onAdded(BreadCrumbsView.Tab tab) {
                Log.e("BreadCrumbsView", "BreadCrumbsView.OnTabListener#onAdded tab:" + tab.getIndex());
                addFragment(tab);
            }

            @Override
            public void onActivated(BreadCrumbsView.Tab tab) {
                Log.e("BreadCrumbsView", "BreadCrumbsView.OnTabListener#onActivated tab:" + tab.getIndex());
            }

            @Override
            public void onRemoved(BreadCrumbsView.Tab tab) {
                Log.e("BreadCrumbsView", "BreadCrumbsView.OnTabListener#onRemoved tab:" + tab.getIndex());
                removeLastFragment();
            }
        });
        Button btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder("Tab");
                sb.append(breadCrumbsView.getCurrentIndex()+1);
                // TODO: 2020/5/26 3:35 PM 传递参数，这里的map数据会存放在tab上，在创建Fragment时可以通过tab.getValue获取。
                HashMap<String, String> map = new HashMap<>();
                breadCrumbsView.addTab(sb.toString(), map);
            }
        });
    }

    private void addFragment(BreadCrumbsView.Tab tab) {
        // TODO: 2020/5/26 3:26 PM 在这里添加你自己的Fragment，tab#getValue返回的是创建Tab时传入的数据，可以根据这些数据创建你自己想要的Fragment
        Fragment fragment = BlankFragment.newInstance(String.format("我是第%d个Fragment", tab.getIndex()), "" + tab.getIndex());
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment, String.valueOf(tab.getIndex()))
                .show(fragment)
                .addToBackStack(null)
                .commit();
        fragments.add(fragment);
    }

    /**
     * 移除最后一个Fragment，显示倒数第二个Fragment
     */
    private void removeLastFragment() {
        if (fragments != null && fragments.size() > 1) {
            getSupportFragmentManager().popBackStackImmediate();
            fragments.removeLast();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .show(fragments.getLast())
                    .commit();
            fragmentManager.executePendingTransactions();
        }
    }

    @Override
    public void onBackPressed() {
        if (fragments != null && fragments.size() > 1) {
            breadCrumbsView.selectAt(fragments.size() - 1 - 1);
        } else {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("确认退出么?")
                    .setCancelable(true)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).create();
            dialog.show();
        }
    }
}
