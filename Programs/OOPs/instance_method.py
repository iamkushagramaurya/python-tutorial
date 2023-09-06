class Person: # we can use first latter as small but for ci=onvertion we use FIRST LATTER CAPITAL INCLASS
    def __init__(self,first,last):
        print("method activated")
        self.first=first
        self.last=last
    def fullname(self):
        return(f"{self.first} {self.last}")

        
obj=Person("Kushagra","Maurya")#obj=self
print(obj.fullname())
print(Person.fullname(obj))# same as line 11