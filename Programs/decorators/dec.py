from functools import wraps


# def f1():
#     print ("f1")
# def f2():
#     print ("f2")
# f1()
# f2()
def dec(any_func):
    @wraps(any_func)##### imported to print doc string of f2 function otherwise printdoc string of wrapper functioon

    def wapper(*args,**kwagrs):
        """honey"""
        print("this is aweosome function")
        return any_func(*args,**kwagrs)
    return wapper
@dec # syntactic sugar
def f1(a):
    print (f"f1{a}")
f1(444) 
@dec
def f2(a,b):
    """yoyo"""
    return a+b
print(f2(2,3))
print(f2.__doc__)



