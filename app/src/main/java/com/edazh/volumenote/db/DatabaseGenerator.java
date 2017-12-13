package com.edazh.volumenote.db;

import android.arch.persistence.room.RoomOpenHelper;

import com.edazh.volumenote.db.entity.BillEntity;
import com.edazh.volumenote.db.entity.FolderEntity;
import com.edazh.volumenote.db.entity.WoodEntity;
import com.edazh.volumenote.model.Bill;
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
            FolderEntity folder = new FolderEntity("folder " + (i + 1));
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
                BillEntity bill = new BillEntity(folder.getId(), "bill " + i);
                bills.add(bill);
            }
        }
        return bills;
    }

    public static List<WoodEntity> generatorWoods(List<BillEntity> bills) {
        List<WoodEntity> woods = new ArrayList<>();
        Random random = new Random();
        for (Bill bill : bills) {
            int woodsCount = random.nextInt(20) + 1;
            for (int i = 0; i < woodsCount; i++) {
                for (int j = 0; j < 20; j++) {
                    WoodEntity wood = new WoodEntity(bill.getId(), String.valueOf(i + 1), String.valueOf(i + j), "料头", String.valueOf(i * j + i + j));
                    woods.add(wood);
                }
            }
        }
        return woods;
    }
}
