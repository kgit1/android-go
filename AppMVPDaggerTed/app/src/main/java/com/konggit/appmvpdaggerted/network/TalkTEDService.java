package com.konggit.appmvpdaggerted.network;

import android.app.Application;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.string.InFileStringObjectPersister;

/**
 * Created by erik on 12.01.2018.
 */

public class TalkTEDService extends SpiceService {
    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();
        InFileStringObjectPersister tedPersister = new InFileStringObjectPersister(application);
        cacheManager.addPersister(tedPersister);
        return cacheManager;
    }

    @Override
    public int getThreadCount() {
        return 3;
    }
}
