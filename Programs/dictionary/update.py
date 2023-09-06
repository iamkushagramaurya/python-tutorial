user={
    'name':"kUSHAGRA MAURYA",
    'WORKING':'LEARNING PYTHON'
}
thisdict = {
  "brand": "Ford",
  "model": "Mustang",
  "year": 1964

}
# upadate method add a dict to another dict
user.update(thisdict) 
print(user)


thisdict = {
  "brand": "Ford",
  "model": "Mustang",
  "year": 1964,
  "name": "bmw" # same key

}


#  we can update value of key buy passind new value with same key
user.update(thisdict) 
print(user)