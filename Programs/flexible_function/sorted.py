tuple=("Doraemon","Spd","Shinchan","Doraemon","Spd","Shinchan")
print(sorted(tuple))
n=[
{"name":"kuhagra","score":34},
{"name":"kuhagra","score":33},
{"name":"kuhagra","score":37} 
]
print(sorted(n,key= lambda item:item["score"]))
print(sorted(n,key= lambda item:item["score"],reverse=True))
