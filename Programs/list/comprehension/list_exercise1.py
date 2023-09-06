def rev(l):
    return[i[::-1] for i in l]
    
print(rev(["hello","my", "name","is"]))
    
def intstore(num):
    print(num)
    return[str(i) for i in num if type(i)== int]


l2=[1,2,3,4,"hello",['yo','yo','yo']]
print(intstore(l2))

def ifelse(d):
    return[i*2 if (i%2==0) else (-i) for i in d]
l3=[1, 2, 3, 4]
print(ifelse(l3))

