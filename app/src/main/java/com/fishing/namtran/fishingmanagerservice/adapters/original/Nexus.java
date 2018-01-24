package com.fishing.namtran.fishingmanagerservice.adapters.original;

/**
 * Created by miguel on 13/02/2016.
 */
public class Nexus {
    public final String type;
    public final String[] data;

    public Nexus(String type) {
        this.type = type;
        data = null;
    }

    public Nexus(String name, String company, String version, String api, String storage, String inches, String ram) {
        this.type = null;
        data = new String[] {
                name,
                company,
                version,
                api,
                storage,
                inches,
                ram };
    }

    public Nexus(String type, String name, String company, String version, String api, String storage, String inches, String ram) {
        this.type = type;
        data = new String[] {
                name,
                company,
                version,
                api,
                storage,
                inches,
                ram };
    }

    public Nexus(String order, String fullname, String dateIn, String dateOut1, String dateOut, String totalHours, String totalFish, String buyFish, String moneyHire, String totalMoney, String note) {
        this.type = null;
        data = new String[] {
                order,
                fullname,
                dateIn,
                dateOut1,
                dateOut,
                totalHours,
                totalFish,
                buyFish,
                moneyHire,
                totalMoney,
                note };
    }

    public boolean isSection() {
        return data == null;
    }
}