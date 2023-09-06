
# from typing import Iterator
# 

# Generators are Iterator



l=[1,2,3,4,5] #iterable
i= iter(l)
print(next(i))
print(next(i))
print(next(i))
print(next(i))
print(next(i))


print(map(lambda a:a**2,l)) #iterator
n=map(lambda a:a**2,l)
print(next(n))
print(next(n))
print(next(n))
print(next(n))
print(next(n))
for a in map(lambda a:a**2,l):
    print(a)
