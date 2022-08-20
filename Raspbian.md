# SurfaceRT-Images / Raspbian

<img width="35%" align="right" src="https://raw.githubusercontent.com/e3ndr/SurfaceRT-Images/main/Pictures/Raspbian.png" />

<br />
<br />

Here you fill find Raspbian images pre-modified with the grate-driver kernel and all the necessary modules for booting Linux on the SurfaceRT.  
Just download an image and follow the instructions below!

[Images](https://mega.nz/folder/ohZzDBJY#W2iOMkD2W_QcMloeyBYnTA)

<br />

## Hardware Support

### What's working

-   Touch Cover
-   Touch Screen
-   Screen Brightness
-   WiFi
-   SD Card
-   USB
-   HDMI (w/ Audio!)
-   Battery & Charging (The percentage in the status bar occasionally shows as 0%, depends on the DE you use)
-   Audio

### What's not working:

-   Hardware Acceleration (Bad graphical performance, 2D and 3D)
-   Bluetooth (You can use a USB adapter)
-   Camera
-   CPU L2 Cache, cpuidle (Bad performance)

## Prerequisites:

-   [A Surface RT with secure boot disabled.](https://jwa4.gitbook.io/windows/tools/surface-rt-and-surface-2-jailbreak-usb)
-   Make sure your Surface is somewhat charged, it's recommended you keep it plugged in during the install process.
-   A USB drive around 8-16GB.

## How to use:

1. Burn any of the [images](https://mega.nz/folder/ohZzDBJY#W2iOMkD2W_QcMloeyBYnTA) to a USB drive using your favorite imager (Rufus, Balena, etc).
2. Boot the USB drive by holding VOL DOWN and pressing power, release after you see the Surface logo.
3. Be patient! First time setup takes long enough, and the Surface makes this process even slower due to a lack of CPU features.
4.  - If you selected a Desktop image: Go through the first time setup. **Note**: Clicking reboot will NOT reboot you back to the USB drive, you will have to shutdown and start your Surface using the volume key method instead.
    - If not: Configure your keyboard layout, region, and wifi via `sudo raspi-config`.
5. Profit!

## How to move the installation to your internal storage:

1. Use `sudo dd if=/dev/sda of=/dev/mmcblk0 status=progress bs=10M` to copy the entire drive to the eMMC of the Surface.
    - **Note**: you can safely hit CTRL+C after DD writes ~5GB, after that is just blank space.
    - Advanced users can manually adjust the dd command to move the rootfs to an SD card (for more storage); refer to [this entry](https://openrt.gitbook.io/open-surfacert/surface-rt/linux/booting/kernel-parameters#root) in the OpenRT wiki for device ids.
2. Mount the eMMC (`sudo mkdir /tmp/emmc && sudo mount -o rw /dev/mmcblk0p1 /tmp/emmc`).
3. `cd` to the newly mounted directory (`cd /tmp/emmc`).
4. Edit `startup.nsh` (`sudo nano startup.nsh`), change the `root=` parameter to be `root=/dev/mmcblk0p2`, you can also remove `rootwait`.
5. Reboot (`sudo reboot now`) and unplug your USB drive.
6. Profit!

## Housekeeping:

1. Use `sudo raspi-config` to expand the filesystem (do this AFTER moving to internal or SD Card storage, if you're going to do that).
2. Update and upgrade your apt repositories (`sudo apt update`), then upgrade (`sudo apt upgrade`).
3. Optionally, you can install a different desktop environment using [these](https://raspberrytips.com/upgrade-raspbian-lite-to-desktop/) instructions.
4. Profit!

## Credits

-   The OpenRT team for their [documentation](https://openrt.gitbook.io/open-surfacert/surface-rt/linux/root-filesystem/distros/raspberry-pi-os) on modifying linux images to run on a SurfaceRT.
-   @jwa4 for their instructions on how to apply Yahallo and unlock the bootloader of the SurfaceRT.
-   [This](https://powersj.io/posts/raspbian-edit-image/#mounting-the-image) blog post by Joshua Powers which helped me get my footing when it came to mounting the RaspberryPI images.
-   @imbushuo for Yahallo
-   @never_released and @TheWack0lian for their discovery of the Golden Keys exploit.
-   Both the GrateLinux and OpenRT teams for their [Linux kernel](https://github.com/Open-Surface-RT/grate-linux) for the Tegra3 SOC.
-   Everyone behind Raspbian and the RaspberryPI, software support for armhf is great because of them.
