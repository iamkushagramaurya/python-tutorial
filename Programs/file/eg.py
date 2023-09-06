with open("eg.txt","r") as rf:
    with open("salary","w") as wf:
        data =rf.read()
        if "," in data:
            data=data.replace(","," salary is ")
        wf.write(data)

with open("eg.txt","r") as rf:
    with open("salary1","a") as wf:
        for line in rf.readlines():
            name,sal=line.split(",")
            wf.write(f"{name} salary is {sal}")


