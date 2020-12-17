# MacGems: Explore Macalester College

MacGems is an Android app using Firebase to provide Macalester College students with locations around campus. It promotes the sharing of experiences through commenting: each building, lawn, and hidden corner around the campus will now be known and accessible to the student body, allowing for a full-blown college experience!

This project currently only works on Android devices.

### Set-up
This project was written for Android Studio 11.0., API level 30.

1. Install Android Studio.
2. Ensure that the Android Gradle Plugin Version is set to 4.1.1, and the Gradle Version to 6.5.
3. The SDK developer tools that need to be installed are:
      - Android SDK Build-Tools
      - Android Emulator
      - Android SDK Platform-Tools
      - Intel x86 Emulator Acelerator (HAXM installer)
To install, go to Tools/SDK Manager/Appearance and Behavior/System Settings/Android SDK/SDK Tools.
4. The following Android Studio dependencies must be installed:
      - annotation:1.1.0
      - appcompact:1.2.0
      - constraintlayout:2.0.4
      - espresso-core:3.3.0
      - firebase-firestore:22.0.0
      - junit:1.1.2
      - legacy-support-v4:1.0.0
      - lifecycle-livedata-ktx:2.2.0
      - lifecycle-viewmodel-ktx.2.2.0
      - material:1.2.1
      - multidex:1.0.3
      - navigation-fragment:2.3.1
      - navigation-runtime:2.3.1
      - navigation-ui:2.3.1
      - play-services-maps:17.0.0
To install, go to File/Project Structure/Dependencies/+.
5. The prefered device to run the code on is Pixel 3a XL API 30.
      
### How to run MacGems?
To run the app on an Android device:
1. Plug a phone that supports Android and Developer settings into a computer via cable.
2. Run the app through Android Studio using the physical device as the emulator. This will automatically install the app on the phone.
3. To use again, find the app on your device and run.

To run the app on the Android Emulator:
1. Select a preferred device (Available devices/AVD Manager/Create Virtual Device).
2. Click the run button.

### How to navigate and use MacGems?
When you open the app, the first page will ask for your email address and a password. If this is your first time logging in, you will want to click on the writing below the Sign In button.
<p align="center">
  <img width="460" height="300" ![Alt text](https://github.com/zcok29/ApronzApp/blob/screenshots/loginpage.jpg?raw=true "Login Pic")>
</p>
That will take you to a page where you will be able to enter a preferred username and log in with you Macalester email and a password of choice. 

Next, you will stumble upon the list of locations.
![Alt text](https://github.com/zcok29/ApronzApp/blob/screenshots/loginpage.jpg?raw=true "Locations Pic")

You can scroll and look at all the options. If you are interested to find out more about a location of choice, click the Explore button.

That will take you to the location's personalized comment page.
![Alt text](https://github.com/zcok29/ApronzApp/blob/screenshots/commentpage.jpg?raw=true "Comments Pic")

You can scroll through all the comments, or you can submit your own by typing a desired message into the Add Comment box and clicking the orange Add button. If you want to take a better look at a particular comment, click View More.

The first page also offers the possibility of adding your own location to the list, allowing other users to get to know your favorite spots around campus. 
![Alt text](https://github.com/zcok29/ApronzApp/blob/screenshots/addlocationscrean.jpg?raw=true "Add Location Pic")

Simply input the name of the location, contact information, and an address so your hidden gem can become a shared treasure! 

### Authors
Ronan Wallace, Yijie (Apple) Guo, Zala Cok
