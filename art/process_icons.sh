#!/bin/bash

#Requires Inkscape to be installed.

#This script scales and creates images at the correct dpi level for Android launcher icons.
#It gets placed in a folder called res/drawable/_source_images/ in your
#Android project along with all your svg files.
#To use simply run the create_images script from its folder and it will generate images for all the svg files.

for f in ic_launcher_*.svg;
do
	echo "Processing $f"
	inkscape -w 96 -e ../res/drawable-xhdpi/${f/.svg}.png ./$f
	inkscape -w 72 -e ../res/drawable-hdpi/${f/.svg}.png ./$f
	inkscape -w 48 -e ../res/drawable-mdpi/${f/.svg}.png ./$f
	inkscape -w 36 -e ../res/drawable-ldpi/${f/.svg}.png ./$f
done

for f in ic_menu_*.svg;
do
	echo "Processing $f"
	inkscape -w 48 -e ../res/drawable-xhdpi/${f/.svg}.png ./$f
	inkscape -w 36 -e ../res/drawable-hdpi/${f/.svg}.png ./$f
	inkscape -w 24 -e ../res/drawable-mdpi/${f/.svg}.png ./$f
	inkscape -w 18 -e ../res/drawable-ldpi/${f/.svg}.png ./$f
done

