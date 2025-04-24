<?php
session_start();

// Generate a 5-character random code
$code = substr(str_shuffle("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, 5);
$_SESSION['captcha'] = $code;

// Create image (100x30 pixels)
$image = imagecreatetruecolor(100, 30);

// Define colors
$bg = imagecolorallocate($image, 255, 255, 255); // White background
$text_color = imagecolorallocate($image, 0, 0, 0); // Black text
$line_color1 = imagecolorallocate($image, 150, 150, 150); // Gray lines
$line_color2 = imagecolorallocate($image, 100, 150, 200); // Blue lines

// Fill background
imagefill($image, 0, 0, $bg);

// Draw the CAPTCHA text (use a bold font if available)
imagestring($image, 5, 10, 8, $code, $text_color);

// Add wavy scribbles
for ($i = 0; $i < 10; $i++) {
    $x1 = rand(0, 100);
    $y1 = rand(0, 30);
    $x2 = $x1 + rand(-20, 20);
    $y2 = $y1 + rand(-10, 10);
    $color = ($i % 2 == 0) ? $line_color1 : $line_color2; // Alternate gray and blue
    imageline($image, $x1, $y1, $x2, $y2, $color);
}

// Output image
header("Content-Type: image/png");
imagepng($image);
imagedestroy($image);
?>
