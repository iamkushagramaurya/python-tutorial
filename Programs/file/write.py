with open("filee.txt","w") as f: #"w " delete all the previous data
    f.write("yoyo")



with open("filee.txt","a") as f: #"a" append new data with the previous data also create a file if not avialbe
    f.write("honey\n") 




with open("filee.txt","r+") as f: #"r+" we can read and write with this method it will not create any file, overrite old date with new
    f.seek(len(f.read()))
    f.write("Singh\n") 