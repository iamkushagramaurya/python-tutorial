s=input("Enter a String \n")
i=0
temp=""
while i<len(s):
    if s[i]in temp:
        i+=1
    else:
        print(f"{s[i]} : {s.count(s[i])}")
        temp+=s[i]
        i+=1
