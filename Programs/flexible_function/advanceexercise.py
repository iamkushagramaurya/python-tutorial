# def average(*agrs):
#     aver=[]
#     for pair in zip(*agrs):
#         aver.append(sum(pair)/len(pair))
#     return aver
# print(average([1,2,3,4,5],[6,7,8,9,10]))

average=lambda *args: [(sum(pair)/len(pair)) for pair in zip(*args)]
print(average([1,2,3,4,5],[6,7,8,9,10]))