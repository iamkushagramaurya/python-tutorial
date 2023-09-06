import time
from functools import wraps
def dectime(any):
    @wraps(any)
    def wrapper(*args,**kwargs):
        print(f"executing { any.__name__}")
        t1=time.time()
        r=any(*args,**kwargs)
        t2=time.time()
        t=t2-t1
        print(f"total tiime is {t}")
        return r
    return wrapper
@dectime
def square(x):
    return[ i**2 for i in range(1,x+1)]
print(square(10000))
k=input("enter to exit")