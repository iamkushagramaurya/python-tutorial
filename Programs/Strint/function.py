# def add(a,b):
#     return a+b
# print(add(4,5))
def greater(a,b):
    if a>b:
        return a
    return b                                                           
print(f"{greater(4,5)} is greater")                                    
def greatest(a,b,c):                                                   
    if greater(4,5)>c:                                                 
        return greater(4,5) 
    return c
print(f" { greatest(4,5,7) } is Greatest")