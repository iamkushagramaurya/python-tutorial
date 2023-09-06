import os, shutil
dictextentions={"audio_ext":(".mp3",".ogg",".m4a",".flac"),
               "video_ext" : (".mp4",".mkv",".MKV",".flv"),
            "doc_ext":(".doc",".pdf" ,".txt")}
path=input("Enter the path of folder")
# os.chdir(r"E:\project")
# print(os.getcwd())
# # item=os.listdir()
# for item in os.listdir():
#     for i in audioext:
#         if item.endswith("i"):
def file_finder(folderpath,file_extention):
    files=[]
    for file in os.listdir(path):
        for extn in file_extention:
            if file.endswith(extn):
                files.append(file)
    return files
# print(file_finder(path,audioext))
for extention_type, extentuple in dictextentions.items():
    # print("calling file finder function")
    # print(file_finder(extention_type, extentuple)) 
    foldername=extention_type.split("_")[0]+ "Files"
    folder_path=os.path.join(path,foldername)
    os.mkdir(folder_path)
    for item in file_finder(extention_type, extentuple):
        itempath=os.path.join(path,item)
        newpath=os.path.join(folder_path,item)
        shutil.move(itempath,newpath)