class Circle:
    pi=3.14
    def __init__(self,radius):
        self.r=radius
    def circum(self):
        return Circle.pi*2*self.r
    def area(self):
        return Circle.pi*(self.r*self.r)
c=Circle(4)
print(c.circum())
print(c.area())
