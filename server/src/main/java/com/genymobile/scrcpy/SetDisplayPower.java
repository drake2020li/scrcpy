package com.genymobile.scrcpy;

import com.genymobile.scrcpy.device.Device;
import com.genymobile.scrcpy.util.Ln;

import android.os.Looper;
import android.system.ErrnoException;
import android.system.Os;

/**
 * Handle the setDisplayPower of scrcpy, even if the main process is killed.
 * <p>
 * This is useful to setDisplayPower when scrcpy is closed, even on device disconnection (which kills the scrcpy process).
 */
public final class SetDisplayPower {
    public static void main(String... args) {
        try {
            // Start a new session to avoid being terminated along with the server process on some devices
            Os.setsid();
        } catch (ErrnoException e) {
            Ln.e("setsid() failed", e);
        }

        // Needed for workarounds
        Looper.prepareMainLooper();

        int displayId = Integer.parseInt(args[0]);
        boolean mode = Boolean.parseBoolean(args[1]);

        // Change the power of the main display when mirroring a virtual display
        int targetDisplayId = displayId != Device.DISPLAY_ID_NONE ? displayId : 0;
		Device.setDisplayPower(targetDisplayId, mode);

        System.exit(0);
    }
}
