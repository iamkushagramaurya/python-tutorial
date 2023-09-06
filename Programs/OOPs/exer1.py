class Lappy():
    def __init__(self,name,model,price):
        self.name=name
        self.model=model
        self.price=price
        self.laptop= name +" "+model
    def discount(self,d):
        return (self.price-(self.price*d/100))
p1=Lappy("Asus","Rog",1000000)
p2=Lappy("Acer","predator",150000)
# print(p1.laptop)
# print(p2.laptop)
print(p1.discount(40))

# print(p2)
######################################## exercise 2