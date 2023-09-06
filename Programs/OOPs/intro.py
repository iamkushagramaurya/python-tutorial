class Person: # we can use first latter as small but for ci=onvertion we use FIRST LATTER CAPITAL INCLASS
    def __init__(self,first,last):
        print("method activated")
        self.first=first
        self.last=last
obj=Person("Kushagra","Maurya")#obj=self
obj1=Person("Shadow","Maurya")
print(obj.first)
print(obj1.first)