class Phone:
    def __init__(self,name,model,price):
        self.name=name
        self.model=model
        self._price=max(price,0)
    @property
    def specs(self): #we can use specs function as an instance
            return f"{self.name} {self.model} {self._price}"
    @property
    def price(self):
        return self._price
    @price.setter
    def price(self,newprice):
        self._price=max(0,newprice)

    def call(self,num):
        print(f"calling......{num}")
    def fullname(self):
        return f"{self.name} {self.model}"
obj=Phone("Redmi","note 7",12000)
obj.price=15000
print(obj.price)
print(obj.specs)