cmake_minimum_required(VERSION 3.22.1)
project("edgedetection2")

add_library(
        edgedetection2
        SHARED
        native-lib.cpp)

# Adjust this path to your OpenCV SDK
set(OpenCV_DIR "${CMAKE_SOURCE_DIR}/../../opencv/sdk/native/jni")

find_package(OpenCV REQUIRED)

target_include_directories(edgedetection2 PRIVATE ${OpenCV_INCLUDE_DIRS})
target_link_libraries(
        edgedetection2
        ${OpenCV_LIBS}
        log)
