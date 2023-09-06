import os
os.chdir(r"c:\Python\osmodule") #to change current working directry 


# os.getcwd ---- current working directory


os.getcwd()

# os.mkdir("newfolder") # create a folder

print(os.path.exists("newfolder")) # to check the folder or file exist or not

open('new.txt',"a").close()  # to create a file               

print(os.listdir()) # show all the files and folders 
for item in os.listdir():
    # print(os.getcwd()+"\\" + item)//
    print(os.path.join(os.getcwd(),item))