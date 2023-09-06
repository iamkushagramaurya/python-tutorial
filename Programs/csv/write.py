# from csv import writer
# with open("file.csv","w",newline="") as f:
#     csv_writer=writer(f)
#     # writerow
#     # csv_writer.writerow(["name","age"])
#     # writerows
#     csv_writer.writerows([["name","age"],["Kusharga",19],["Nazira",25]])
from csv import DictWriter
with open("file.csv","w",newline="") as f:
    csv_writer=DictWriter(f,fieldnames=["name","lastname"])
    csv_writer.writeheader()
    # writerow as input dcitionary
    # csv_writer.writerow({
    #     "name":"kushagra",
    #     "lastname":"maurya"
    # })
    # csv_writer.writerow({
    #     "name":"Nazira",
    #     "lastname":"Khatoonn"
    # })
    csv_writer.writerows([
        {"name":"kushagra",'lastname':"maurya"},
    {"name":"Nazira","lastname":"Khatoonn"}])
