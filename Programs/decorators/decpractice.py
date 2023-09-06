from functools import wraps
def print_func(any):
    @wraps(any)
    def wrapper(*args,**kwargs):
        print(f"you are calling   {any.__name__}  function ")
        print(any.__doc__)
        return any(*args,**kwargs)
    return wrapper
@print_func
def add(a,b):
    """this function takes 2 input and return their sum"""
    return a+b
print(add(3,5))
    