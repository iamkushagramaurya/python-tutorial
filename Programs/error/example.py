class Phone:
    def __init__(self,name):
        self.name=name
class Mobstore:
    def __init__(self):
        self.mobile=[]
    def addmob(self,newmob):
        if isinstance(newmob,Phone):
            self.mobile.append(newmob)
        else:
            raise TypeError("Not a instance")
obj=Phone("redmi note 7")
redmi="Redmi 5"
obj1=Mobstore()
obj1.addmob(obj)
l=obj1.mobile
print(l[0].name)