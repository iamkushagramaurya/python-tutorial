with open("filee.txt","r") as rf:
    with open("file1.txt","w") as wf:
        wf.write(rf.read())