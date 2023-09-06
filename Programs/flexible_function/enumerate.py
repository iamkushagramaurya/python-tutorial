list1=["hello","my", "name","is"]
for pos,name in enumerate(list1):
    print(f"{pos} : {name}")
s="name"
for pos,i in enumerate(list1):
    if s==i:
         print(pos)
