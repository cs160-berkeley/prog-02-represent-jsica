package com.example.jessica.reps;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WatchListen extends Service {
    public WatchListen() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
