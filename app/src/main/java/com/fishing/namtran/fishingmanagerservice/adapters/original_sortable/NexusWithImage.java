package com.fishing.namtran.fishingmanagerservice.adapters.original_sortable;

import com.fishing.namtran.fishingmanagerservice.adapters.original.Nexus;

import java.util.List;

/**
 * Created by miguel on 06/03/2016.
 */
public class NexusWithImage extends Nexus {
    public List<Integer> resImages;

    public NexusWithImage(String type, List<Integer> resImages) {
        super(type);
        this.resImages = resImages;
    }

    public NexusWithImage(String name, String company, String version, String api, String storage, String inches, String ram) {
        super(name, company, version, api, storage, inches, ram);
    }

    public NexusWithImage(String type, String name, String company, String version, String api, String storage, String inches, String ram) {
        super(type, name, company, version, api, storage, inches, ram);
    }
}
