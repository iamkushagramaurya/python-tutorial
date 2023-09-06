# from csv import reader
# with open("K:\Python\csv\intro.csv","r") as f:
#     cr=reader(f)
#     next(cr)
#     for row in cr:
#         print(row)
from csv import DictReader
with open("C:\Python\csv\intro.csv","r") as f:
    cr=DictReader(f)
    for row in cr:
        print(row['name'])       