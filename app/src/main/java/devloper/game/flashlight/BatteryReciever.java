package devloper.game.flashlight;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.BatteryManager;
import android.widget.ImageView;
import android.widget.TextView;

public class BatteryReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        TextView statusLabel = ((Second) context).findViewById(R.id.statusLabel);
        TextView bateryStatus = ((Second) context).findViewById(R.id.bateryStatus);
        ImageView bateryy_status = ((Second) context).findViewById(R.id.bateryy_status);

        String action = intent.getAction();

        if (action != null && action.equals(Intent.ACTION_BATTERY_CHANGED)) {

            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            String message = "";

            switch (status) {
                case BatteryManager.BATTERY_STATUS_FULL:
                    message = "Dolu";
                    break;

                case BatteryManager.BATTERY_STATUS_CHARGING:
                    message = "Şarj Oluyor...";
                    break;

                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    message = "Pil Durumu!";
                    break;

                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    message = "Şarj Olmuyor...";
                    break;

                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    message = "Bilinmeyen...";
                    break;

            }

            statusLabel.setText(message);

            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            int pacet = level * 100 / scale;

            bateryStatus.setText(pacet + "%");


            Resources res = context.getResources();


            if (pacet >= 90) {
                bateryy_status.setImageDrawable(res.getDrawable(R.drawable.baterry_full));
            } else if (90 > pacet && pacet >= 65) {
                bateryy_status.setImageDrawable(res.getDrawable(R.drawable.bateruy_75));
            } else if (65 > pacet && pacet >= 40) {
                bateryy_status.setImageDrawable(res.getDrawable(R.drawable.batery_half));
            } else {
                bateryy_status.setImageDrawable(res.getDrawable(R.drawable.batery_min));
            }


        }


    }
}
