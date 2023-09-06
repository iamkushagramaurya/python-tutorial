from functools import wraps
def  selected_data(d):
    def dec(any):
        @wraps(any)
        def wrapper(*args,**kwargs):
            if all([type(i)==d for i in args]):
                return any(*args,**kwargs)
            print("wrong input")
        return wrapper
    return dec
@selected_data(str)
def add(*a):
    s=''
    for i in a:
        s+=i
    return s
print(add("Kushagra","Maurya",0))