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
    @staticmethod
    def stat():
        print("Static Method")
    @classmethod 
    def ins(cls):
        return f"You have created {cls.c} instances"
obj1=Instance("Kushagra","Maurya",19)
obj2=Instance("SHadow","Maurya",2)
Instance.stat()
# print(Instance.ins())
# print(obj1.above())

# print(obj1.name())