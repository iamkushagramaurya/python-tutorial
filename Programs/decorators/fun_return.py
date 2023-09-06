# def outer():
#     def inner():
#         print("insside funtion")
#     return inner()########### retur exe3cuted inner function
# a=outer() ###################### a contains inner 

# ##############################################################################################################################################

# def o():
#     def i():
#         print("insside funtion")
#     return i # return non executed inner frunc
# def square(a):
#     return a**2  
# s=square
# l=[1,2,3,4,5,6,7,8,9]
# def f(fun,l):
#     s=[]
#     for i in l:
#         s.append(fun(i))
#     return s
# print(f(square,l))



    ##################################################################################################################################
def power(x):
    def calc(n):
        return n**x
    return calc
cube= power(3) # cude will store calc
print(cube(4))