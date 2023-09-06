def fibbo(n):
    print(0)
    print(1)
    a=0
    b=1
    sum=0
    for i in range(0,n):
        sum=a+b
        print(sum)
        a=b
        b=sum
d=int(input("Enter the limit of fibbonacci Series"))
fibbo(d)