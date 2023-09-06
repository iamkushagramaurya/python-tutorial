class Phone:
    def __init__(self,name,model,price):
        self.name=name
        self.model=model
        self._price=max(price,0)
    def fullname(self):                      #  IT has more specificity rhan repr
        return f"{self.name} {self.model}"
    def __repr__(self):
        return f"{self.name} {self.model} {self._price}"

    def __str__(self):
        return f"{self.name} {self._price}"
    def __len__(self):
        return len(self.fullname())
    def __add__(self,other):
        return self._price + other._price
obj=Phone("REdmi","note 7",12000)
obj1=Phone("REdmi","note 8",15000)
print(obj)
print(obj+obj1)
#  print(str(obj))
# print(repr(obj))
# print(len(obj))