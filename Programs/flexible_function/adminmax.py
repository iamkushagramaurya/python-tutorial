names=[
{"name":"kuhagra","score":37},
{"name":"kuhagra","score":24}



]
print((max(names,key= lambda item:item.get("score"))))

n={
"s1":{"name":"kuhagra","score":34},
"s2":{"name":"kuhagra","score":36},
"s3":{"name":"kuhagra","score":37},  
}
print(max(n,key= lambda item:n[item]["score"]))
