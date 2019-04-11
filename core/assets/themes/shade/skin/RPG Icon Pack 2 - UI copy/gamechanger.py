from glob import *
import os

for pic in glob("AddSub/*"):
    
    s=pic.split("/")
    s[1]=s[0].lower()+"-"+s[1]
    s="/".join(s)
    os.rename(pic,s)
