import os

cmd = 'gradle -v'
b = os.system(cmd)

cmd = 'gradle clean'
os.system(cmd)

cmd = 'gradle unit'
a = os.system(cmd)
print ("here : " +str(a))
print ("\n\nhere : " +str(b))