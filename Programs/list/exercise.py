def square(m):
    n=[]
    for i in m:
        n.append(i*i)
    return n
numbers=list(range(1,11))
print(square(numbers))   