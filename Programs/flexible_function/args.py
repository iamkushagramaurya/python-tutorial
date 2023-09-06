def add(*args):
    sum=0
    for i in args:
        sum+=i
    return sum
print(add(1,2,3,4,5,6,7,8,9))

################################################ normal paramters with * ############################
def mul(m,*args):
    n=1
    for i in args:
        n*=i
    return n
print(mul(4,5,6,))

############################################## both args ################################################
def arg(*args):
    s=0
    for i in args:
        s+=i
    print(s)
arg(*[1,2,3,5]) #unpack other elements


        