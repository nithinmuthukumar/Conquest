import glob
from pygame import *

x = [i for i in open("readme.txt", "r").read().strip().split(",")]
files = sorted(glob.glob("icons/*"), key=lambda x: int(x.split("_")[2].split(".")[0]))
print(files)
for i in range(len(x)):
    image.save(image.load(files[i]), "c/" + x[i] + ".png")
