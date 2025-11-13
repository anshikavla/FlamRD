const img = document.getElementById("frame") as HTMLImageElement;
const fpsSpan = document.getElementById("fps") as HTMLSpanElement;
const resSpan = document.getElementById("resolution") as HTMLSpanElement;

// TODO: Replace with a real base64 exported from Android later
const sampleBase64 = "PUT_BASE64_STRING_HERE";

img.src = "data:image/png;base64," + sampleBase64;
fpsSpan.textContent = "15";
resSpan.textContent = "640x480";
