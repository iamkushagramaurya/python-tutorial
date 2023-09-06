with open("K:\\Python\\file\\practice.html","r") as web:
    with open("out.txt","a") as wf:
        for line in web.readlines():
            if "<a href=" in line:
                position=line.find("a href")
                first_quote=line.find('\"',position)
                second_quote=line.find('\"',first_quote+1)
                url=line[first_quote+1:second_quote]
                wf.write(url)