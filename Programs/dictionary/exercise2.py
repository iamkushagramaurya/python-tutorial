user={}
for i in  range(4):
    a,b=input("Enter key and value seprated by comma").split(",")
    user[a]=[b]
for i in user:
    print(f"Key is : {i} : value is : {user[i]}")
for key,value in user.items():
    print(key,value)

