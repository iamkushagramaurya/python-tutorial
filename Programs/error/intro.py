# def add(a,b):
#     if type(a)==int and type(b)==int:
#         return a+b
#     raise TypeError("Wrong datatype")

# print(add('3','5'))
class Animal:
    def __init__(self,name):
        self.name=name
    def sound(self):
        raise NotImplementedError("you have to define seprate method in subclass")
class Krishna(Animal):
    def __init__(self, name,breed):
        super().__init__(name)
        self.breed=breed
    def sound(self):
        return "Bhai Party"
class Cat(Animal):
    def __init__(self, name,breed):
        super().__init__(name)
        self.breed=breed
    def sound(self):
        return "Meow Meow"
dog =Krishna('Moniter','desi')
print(dog.sound())