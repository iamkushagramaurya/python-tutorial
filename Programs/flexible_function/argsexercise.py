def power(n,*args):
    return[i**n  for i in args]
print(power(2,*[1,2,3,4,5,6,7]))