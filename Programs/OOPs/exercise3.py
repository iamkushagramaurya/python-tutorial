class Person: # we can use first latter as small but for ci=onvertion we use FIRST LATTER CAPITAL INCLASS
    c=0
    def __init__(self,first,last):
        Person.c=Person.c+1
        print("method activated")
        self.first=first
        self.last=last
obj=Person("Kushagra","Maurya")#obj=self
obj1=Person("Shadow","Maurya")
print(Person.c)
print(obj.first)
print(obj1.first)