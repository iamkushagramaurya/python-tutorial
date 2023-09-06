from csv import DictReader, DictWriter
from csv import DictReader
with open("C:\Python\contacts.csv","r") as rf:
    with open("contactsort.csv","w") as wf:
        cwrite=DictWriter(wf,fieldnames=["name","no."])
        cread=DictReader(rf)
        cwrite.writeheader()
        for row in cread:
            nm,n=row['Name'],row["Number"]
            cwrite.writerow(
                { "name":nm,
                "no.":n}
            )