from functools import wraps
def dec(any):
    @wraps(any)
    def wrapper(*args,**kwargs):
        if all([type(i)== int for i in args]):
            return any(*args,**kwargs)
        # d=[]
        # for i in args:
        #     d.append(type(i)==int)
        # if all(d):
        #         print("hello")
        #         return any(*args,**kwargs)
        # else:
        print("wrong input")
    return wrapper


@dec
def add(*a):
    s=0
    for i in a:
        s+=i
    return s
print(add(1,2,3,4,5,6,7,[1,2,3,4]))
    # solving list error using decorator
# 8004914575