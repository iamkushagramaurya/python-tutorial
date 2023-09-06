class Phone:
    def __init__(self,name,model,price):
        self.name=name
        self.model=model
        self._price=max(price,0)
    def fullname(self):
        return f"{self.name} {self.model}"
    def call(self,num):
        return f"Calling {num}......."
class Smartphone(Phone):
    def __init__(self,name,model,price,bc,ram,mem):
        super().__init__(name,model,price)   
        self.bc=bc
        self.ram=ram
        self.mem=mem
    def fullname(self):
        return f"{self.name} {self.model} and price {self._price}"
class Flagship(Smartphone):
    def __init__(self,name,model,price,bc,ram,mem,fc):
        # Phone.__init__(self,name,model,price)
        super().__init__(name,model,price,bc,ram,mem)   
        self.fc=fc
  
obj=Phone("Samsung","3200",2000)
obj1=Smartphone("Redmi","note 7",12000,'48 mp','3gb','32gb')
obj2=Flagship("Google","Pixel 4",96000,"32mp","8gb","128gb","16mp")

print(f"{obj2.fullname()}")
print(isinstance(obj1,Flagship)) #used to check from which class object is belongs
print(issubclass(Flagship,Phone))