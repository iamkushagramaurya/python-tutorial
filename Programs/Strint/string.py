a="       hello            "     
# k,m= input("Enter a word and a character ").split(",")
b="hel       l0"
# print(f"Length {len(k)} {(k.lower()).count(m)}")
print(a.rstrip())
print(a.strip())
                                              #all spaces in string
print(a.lstrip())
print(b.replace(" ","")) 

                                          #find method
c="My name is Kushagra Maurya. This am a code."
print(c.find("am"))

                                                              # center method

d="kushagra"
print(d.center(10,"#"))
print(d.center(12,"*"))
print(d.center(14,"@"))

                                                    #more methods
                                

e="dora"
print(e+"emon")
e+="emon"
print(e[::-1])