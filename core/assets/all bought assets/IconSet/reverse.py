# reverse.py
# reverses sprites in sprite folders
import glob
import os
from pygame import *

startdir = os.getcwd()
folders = glob.glob("ELF/*")
print(folders)
flipped = {v: [transform.flip(image.load(i), True, False) for i in glob.glob(v + "/RIGHT/*.png")] for v in folders}
for v in flipped:
    os.chdir(v + "/LEFT")
    for i, ii in enumerate(flipped[v][::-1]):
        image.save(ii, "LEFT" + str(i) + ".png")
    os.chdir(startdir)
