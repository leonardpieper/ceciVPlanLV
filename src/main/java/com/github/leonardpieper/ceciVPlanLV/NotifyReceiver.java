package com.github.leonardpieper.ceciVPlanLV;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotifyReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
            context.startService(new Intent(context, NotifyService.class));
    }
}
