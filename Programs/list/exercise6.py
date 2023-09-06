def listcount(m):
    n=[]
    count=0
    for i in range(len(m)):    
        if type(m[i])==list:
            count+=1
    return count
list1=[1,2,[1,2,3,4],3,4,4 ,[4,5,67,45,5],[3,4,5,6,3]]
print(listcount(list1))