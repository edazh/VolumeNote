package com.edazh.volumenote.ui.activity;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.edazh.volumenote.R;
import com.edazh.volumenote.model.Bill;
import com.edazh.volumenote.model.Folder;
import com.edazh.volumenote.ui.fragment.BillListFragment;
import com.edazh.volumenote.ui.fragment.FolderListFragment;
import com.edazh.volumenote.ui.fragment.WoodListFragment;

public class MainActivity extends SingleFragmentActivity {

    private Toast mToast;

    public void show(Folder folder) {
        BillListFragment fragment = BillListFragment.forFolder(folder.getId());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("BillList")
                .replace(R.id.fragment_container, fragment, null)
                .commit();
    }

    public void show(Bill bill) {
        WoodListFragment fragment = WoodListFragment.forBill(bill.getId());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("WoodList")
                .replace(R.id.fragment_container, fragment, null)
                .commit();
    }

    public void showToast(String msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    protected Fragment createFragment() {
        return FolderListFragment.newInstance();
    }
}
