def rev_element(m):
    n=[]
    for i in range(len(m)):
        ele=m[i]
        n.append(ele[::-1]) 
    return n

list1=["hello","my", "name","is"]
print(rev_element(list1))