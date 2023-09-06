def count(n):
    dic={}
    for i in n:
        dic[i]= n.count(i)
    return dic
print(count("Kushagra")) 