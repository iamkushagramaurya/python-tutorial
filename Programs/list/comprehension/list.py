a=[]
for i in range(1,11):
    a.append(i**2)
print(a)
b=[i**2 for i in range(1,11)]
333###########################################################################################################333333
c=[]
for i in range(1,11):
    c.append(-i**2)
print(c)
d=[-i**2 for i in range(1,11)]
print(d)
##################################################################################################
list1=["hello","my", "name","is"]
l1=[]
for i in list1:
    l1.append(i[0])
print(l1)

l2=[i[0] for i in list1]
print(l2)