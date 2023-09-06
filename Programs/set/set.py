########### we can store strinf and number in sets we can't store list tuples and dictionary in sets
# It doesnot print in order

from typing import Union


s=[1,2,3,4,5,6,7,7,88,89,9,0,0]
l=[1,1,1,1,1,1, 2,2,2,2,22,33,3,3,3,3,3,34,4,4,4]
# print(s,l)
l=set(l) #///////// removes duplicate data
print(l)
################################ Fnction #################################
# remove() if element is not present throws an error
# discard() we can use is in the place of remove method it throws no error
# add()
# clear()
# copy()
# in keyword
s1={1,2,3,4,5}
s2={1,3,5,7,8}
# union{1,2,3,4,5,7,8}
union_set= s1|s2
print(union_set)
# intersectio
print(s1&s2)