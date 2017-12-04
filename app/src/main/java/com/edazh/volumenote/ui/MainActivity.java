package com.edazh.volumenote.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.edazh.volumenote.R;
import com.edazh.volumenote.model.Folder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            FolderListFragment fragment = new FolderListFragment();

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, FolderListFragment.TAG).commit();
        }
    }

    public void show(Folder folder) {
        BillListFragment fragment = BillListFragment.forFolder(folder.getId());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("BillList")
                .replace(R.id.fragment_container, fragment, null)
                .commit();
    }
}
