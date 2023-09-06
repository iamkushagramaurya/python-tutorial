def  evod(m):
    even=[]
    odd=[]
    for i in range(len(m)):
        if m[i]%2==0:
            even.append(m[i])
        else:
            odd.append(m[i])
    sortlist=[]
    sortlist.append(even)
    sortlist.append(odd)
    return sortlist
print(evod(list(range(1,11))))
