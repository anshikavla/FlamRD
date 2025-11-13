Real-Time Edge Detection 

This project shows a basic real-time edge detection system running on Android using the camera, C++ processing, and OpenGL for display.
A small web viewer (TypeScript) is also included to preview a sample processed frame.

Features Implemented

Android

Camera feed captured using Android CameraX
Image processing done in C++ with OpenCV (Canny edge detection)
Processed frames displayed using OpenGL ES
Simple touch-free interface — app starts and shows output directly

Web (TypeScript)

Small webpage that displays a processed frame
Shows FPS and resolution text
Useful for quick checking without opening the Android app

Setup Instructions (Simple)

Android Setup

Install Android Studio
Install:
  SDK 34
  NDK
  CMake
Download the OpenCV Android SDK
Place the OpenCV folder inside your project
Open the project in Android Studio and hit Run

Architecture 

Here is how the app works:
1.Camera
  a.Android captures frames from the device camera.
2.JNI (Bridge)
  a.The frame is sent from Kotlin → C++.
C++ + OpenCV
  a.C++ receives the frame.
  b.OpenCV converts it to grayscale and applies edge detection.
  c.The processed frame is returned to Android.
3.OpenGL ES
  a.The processed image is drawn on the screen using OpenGL.
4.Web Viewer
  a.A basic webpage shows one processed frame with FPS and resolution text.

This is a simple flow built to demonstrate:

1.Android camera usage
2.C++ image processing
3.JNI communication
4.OpenGL rendering
5.TypeScript setup

Folder Structure

app/
  ├── camera/          # Camera feed
  ├── gl/              # OpenGL renderer
  ├── cpp/             # C++ + OpenCV code
  ├── jni/             # JNI bridge
  └── MainActivity.kt  # App entry

web/
  ├── index.html       # Viewer
  ├── src/main.ts      # TypeScript code
  └── assets/          # Images

  Screenshot
  <img width="775" height="527" alt="image" src="https://github.com/user-attachments/assets/da0aea23-38c3-4a0e-b34a-603f88af1ab0" />
