from PIL import Image,ImageEnhance,ImageFilter
img1=Image.open("C:\Python\imagesedit\dora.jpg")
# img1.save("C:\Python\imagesedit\doraemon.png")
# img1.save("C:\Python\imagesedit\doraemon.pdf")
# m=(250,250) # used to set the size
# img1.thumbnail(m)
# img1.save("C:\Python\imagesedit\small.jpg")
sharp= ImageEnhance.Sharpness(img1)
sharp.enhance(10).save("C:\Python\imagesedit\dorasharp.jpg") #0== blur , 1= original, 2 more sharp


# ______________________________ c0l0r ________________________________________
colr= ImageEnhance.Color(img1) #0== black and white , 1= original, 2 more color
colr.enhance(3).save("C:\Python\imagesedit\doracolor.jpg")

# ImageEnhance.Brightness

brig= ImageEnhance.Brightness(img1) #0== black, 1= original, 2 bright   
brig.enhance(2).save("C:\Python\imagesedit\dorabright.jpg")


# ImageEnhance.Contrast

cont= ImageEnhance.Contrast(img1) #0== black, 1= original, 2 bright   
cont.enhance(2).save("C:\Python\imagesedit\doracont.jpg")

# ImageFilter

img1.filter(ImageFilter.GaussianBlur(radius=5)).save("C:\Python\imagesedit\dorablur.jpg") # default radius 2 
