package com.edazh.volumenote.db;

import com.edazh.volumenote.db.entity.BillEntity;
import com.edazh.volumenote.db.entity.FolderEntity;
import com.edazh.volumenote.model.Folder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by edazh on 2017/12/2 0002.
 */

public class DatabaseGenerator {

    public static List<FolderEntity> generatorFolders() {
        List<FolderEntity> folders = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int folderNumber = random.nextInt(20) + 1;
            FolderEntity folder = new FolderEntity();
            folder.setId(i + 1);
            folder.setName("folder " + (i + 1));
            folder.setUpdatedTime(new Date(System.currentTimeMillis()
                    - TimeUnit.DAYS.toMillis(folderNumber - i) + TimeUnit.HOURS.toMillis(i)));
            folders.add(folder);
        }
        return folders;
    }

    public static List<BillEntity> generatorBills(List<FolderEntity> folders) {
        List<BillEntity> bills = new ArrayList<>();
        Random random = new Random();
        for (Folder folder : folders) {
            int billsCount = random.nextInt(20) + 1;
            for (int i = 0; i < billsCount; i++) {
                BillEntity bill = new BillEntity();
                bill.setFolderId(folder.getId());
                bill.setName("bill " + (i + 1));
                bill.setUpdatedTime(new Date(System.currentTimeMillis()
                        - TimeUnit.DAYS.toMillis(billsCount - i) + TimeUnit.HOURS.toMillis(i)));
                bills.add(bill);
            }
        }
        return bills;
    }
}
