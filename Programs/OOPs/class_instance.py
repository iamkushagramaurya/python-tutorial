class Instance:
    c=0
    def __init__(self,f,l,age):
        Instance.c+=1
        self.f=f
        self.l=l
        self.age=age
    def name(self):
        return (self.f + " "+ self.l) 
    def above(self):
        return self.age>18
    @classmethod
    def cons(cls,s):
        first,last,a=s.split(",")
        return cls(first,last,a)
    @classmethod 
    def ins(cls):
        return f"You have created {cls.c} instances"
obj1=Instance("Kushagra","Maurya",19)
obj2=Instance("SHadow","Maurya",2)
obj3=Instance.cons("Kushagra,Maurya,19")
print(obj3.name())
print(obj3.above())
# print(Instance.ins())
# print(obj1.above())

# print(obj1.name())