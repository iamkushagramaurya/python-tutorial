# while True:
#     try:
#         age=int(input("Enter Your Age : "))
#         break
#     except:
#         print("Invalid input")

# if age>18:
#     print("You can play this game")
# else:
#     print("You can\'t play this game")


# def div(a,b):
#     try:
#         return a/b
#     except TypeError:
#         print("You have entered wrong datatype")
#     except ZeroDivisionError:
#         print("{a} can't divided by zero")
#     except:
#         print("unexpected error")
# print(div(2 ,4))
class JhaantBarabarNaamError(ValueError):
    pass

def valid(name):
    if len(name) < 8 :
        raise JhaantBarabarNaamError("Name is too short")
user=input("Enter a name \n")
valid(user)
print(user)
