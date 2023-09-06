user={
    'name':"kUSHAGRA MAURYA",
    'WORKING':'LEARNING PYTHON'
}
####################### Key check #################
if 'name' in user:
    print(user['name'])
    ################################## Print by Values ####################### 
if "kUSHAGRA MAURYA" in user.values():
    print("njkbf")
 #################### loops #################################
for i in user:
     print(i)##################33333 Print Keys of dictionary
    
for i in user.values():
     print(i)##################33333 Print Keys of dictionary

################# values method ###################################
user_values=user.values()
print(user_values)
print(type(user_values))
 ############################ Keys method  ##########################
user_keys=user.keys()
print(user_keys)
################################## items method ##########################
user_items=user.items()
print(user_items)
############################### loops with items #######################################33
for key,values in user.items():
    print(key,values)

##################### get method #################################
print(user.get("name"))
print(user.get("names"))###########333 not a key but not shows error
# print(user['names']) throw error
if user.get("name"):
    print(True)
if user.get("names"):
    print(True)
else:
    print(False)
 ################################## copy methd ######################################
u=user.copy()
print(u)
########################################################### clear method ######################################
u.clear()
print(u)





    





