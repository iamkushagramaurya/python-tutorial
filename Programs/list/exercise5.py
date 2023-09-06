def common(m,n):
    same=[]
    for i in range(len(m)):
        for j in range(len(n)):
            if m[i]==n[j]:
                same.append(m[i])
    return same
list1=[1,2,3,4,5,6,7,8,9,10]
list2=[2,4,6,8,10]
print(common(list1,list2))