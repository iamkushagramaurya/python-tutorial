# age=int(input("enter your age"))
# if age >= 14:
#     print("your are above 14")else:
#         print("no")
#                     # gam
import random

a=random.randint(0,51)
b=int(input("Enter your guess \n"))
for i in range(1,100):
    if a==b:
        print("You won !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! \n ")
        print(f"You guessed number  in {i} Times")
        break
    elif a>b:
        print("Your Guess is too low")
        b=int(input("guess again \n"))
    elif a<b:
        print("Your Guess is too high")
        b=int(input("guess again \n"))
j=0
k="kushagra"
for j in k:
    print(j)


