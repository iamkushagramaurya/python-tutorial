def palin(a):
  return(a.lower()==a[::-1].lower())

print(f" 'naman' is a palindrome string : {palin('Naman')} \n")
