def ten(n):
    for i in range(1,n+1):
        yield(i)
# print(ten(10))

numbers=ten(10)
for num in  numbers:
    print(num)
# for num in ten(10):
#     print(num)