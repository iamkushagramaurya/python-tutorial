from sys import float_repr_style


f=open("astro.txt",'r') #read is default
print(f.read())
print(f.read()) #print only one time beacause cursor is after last word
print(f.tell()) # cursor position


f.seek(0) #refresh the pointer locsation
print(f.readline(),end=" ") #print only one line at a time /// end prevent printing extra blank line in output
print(f.readline(),end=" ") #print only one line at a time
# print(f.readlines(),end=" ") #print all the lines together and forms it in a list
line=f.readlines()
for l in line:
    print(l,end="")
print(f.name)






f.close()