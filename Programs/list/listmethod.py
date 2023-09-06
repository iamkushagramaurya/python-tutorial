list1=["Doraemon","Spd","Shinchan","Doraemon","Spd","Shinchan",]
list2=["hagemaru","hddy mera buddy"]
print(list1)
print(list2)
################# insert(position,value)###################################################
print("insert")
list1.insert(0,"ninja steel") 
print(list1)
############### list1.extend(list2)###### values of list2 will be addeded in list1############
print("extend")
list1.extend(list2)
print(list1)
############ list.pop(position) ###### delete element at that position(no position last element will be deleted)
print("pop")
list1.pop()
print(list1)
list1.pop(0)
print(list1)
############# list.remove(value)##### search element value and delete that elment if 2 same elment it will delete first element

print("remove")
list2.remove("hagemaru")
print(list2)
#################### if "value" in list #######
print("in")
if "doraemon" in list1:
    print("present")
else:
    print("not present")
if "nobita" in list1:
    print("present")
else:
    print("not present")
    ############################## count ##############################
print("count")
print(list1.count("Doraemon"))
#################### sort####### arrange  in alphabetical order
list1.sort()
print(list1)
################ clear ############ delete all elements ##########
# list1.clear()
# print(list1)
###### print(shorted(data structure))##################### direct print
print(sorted(list1))
 ###################### =listnew=list.copy() ####### create copy of list###########
listnew =list1.copy()

print(listnew)
##################### index ############################
print(list1.index("Doraemon"))
































































































