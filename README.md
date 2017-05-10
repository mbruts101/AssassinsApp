# Assassins-Mobile
Project for Data Comm

Virtually implements the Assassins game (see https://en.wikipedia.org/wiki/Assassin_(game))

To run this project you will need:
- A computer
- Android Studio
- Multiple Android Devices (at least 3: 2 for Players and one for Server) along with a USB cable to connect to the computer for the client side app.

Running Instructions:

1. Download Android Studio (https://developer.android.com/studio/index.html)

2. To open the client-side app, within Android Studio, click File > Open > Project, and select the MapsDemo folder. 
   To open the server-side app, within Android Studio, click File > Open > Project, and select the ServerSideApp folder.

3. To test either code base, click the green play/run button at the middle of the top of the screen. A new window dialogue should appear asking you to select which device to launch the application on. If you have no devices plugged in, the computer will prompt to launch an emulator, do not launch the emulator, continue to step 4.
   
4. Plug in your Android device via USB. The built in emulator in Android Studio cannot be used to test this code, as it does
   not have GPS Location Services (client only). For the server-side app, you can run it on a connected Android device, or the built in emulator.

5. Turn on debugging mode and your device should appear under the USB Devices tab.
   Tutorial to turn on debugging mode: (https://www.companionlink.com/support/kb/Enable_Android_USB_Debugging_Mode)

6. Once you've decided which device you'll be testing the code on, click "OK", and Android Studio will build the Gradle and
   run the app on the desired device.

7. To test the apps with each other first run the server app.(Important Note: The port 8080 should be open on the desired network in order for the apps to communicate with eachother)

8. Once the server app is running you may begin to launch the client side apps on multiple Android devices.The server is built to handle a dynamic number of players for the game so player size doesn't matter as long as it is larger than 2.

9. If the client side app crashes when trying to join the game, please make sure that your device is giving the app permission to use the device's gps location.'

10. The server will shuffle the players and targets once a new player joins the game, so make sure all players are in before beginning the game.

11. Once you are ready to begin the game, you may play and hunt down your target. For instructions on how to play the game please check the about page of the client side app. 
