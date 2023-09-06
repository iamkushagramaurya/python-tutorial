class Lappy():
    d=10
    def __init__(self,name,model,price):
        self.name=name
        self.model=model
        self.price=price
        self.laptop= name +" "+model
    def discount(self):
        return (self.price-(self.price*self.d/100))

p1=Lappy("Asus","Rog",1000000)

p2=Lappy("Acer","predator",150000)
# print(p1.laptop)
# print(p2.laptop)
p1.d=20
p2.d=30
print(p1.discount())
print(p2.discount())
# print(p1.__dict__)

