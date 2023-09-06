class A:
    def aa(self):
        return " class A"
    def hello(self):
        return "hello a"
class B:
    def bb(self):
        return " class b"
    def hello(self):
        return "hello b "
class C(B,A):
    pass
objc=C()
print(objc.aa())
print(objc.hello())