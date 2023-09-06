user={
    'name':"kUSHAGRA MAURYA",
    'WORKING':'LEARNING PYTHON'
}
######################## add data ##############################
user['key']="value"
print(user['key'])
print(user)
########################## pop ####################################
poped=user.pop('key')
print(user)
print(type(poped))
print(poped)
############################ popitem ################ remove random key,value pair
p=user.popitem()
print(user.popitem())
print(p)
print(type(p))#################### popitem returns tuple datatype 