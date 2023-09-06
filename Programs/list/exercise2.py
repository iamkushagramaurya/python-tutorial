def reverse(m):
    n=m[::-1]
    return n
list1=["hello","my", "name","is"]
print(reverse(list1))
###########################append##########################
def rev(j):
    k=[]
    for i in range(len(j)):
        k.append(j.pop())
        
    return k
print(rev(list1))
