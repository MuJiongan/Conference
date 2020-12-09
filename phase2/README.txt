Before you start the program, there are a few things you should do.

First, make sure to uncomment line 37 in LogInPresenter - om.createOrganizer("Jonathan", "chenjo14", "12345678", getNewID());
This will add an organizer account to the program.


Then, in Intellj, run your virtual device and go to view -> tool windows -> device file explorer. We need to manually add some .ser files into our virtual device.
Go to /data/data/com.example.conference/files and add these files:
attendeemanager.ser
eventmanager.ser
messagemanager.ser
organizermanager.ser
roommanager.ser
speakermanager.ser
vipeventmanager.ser
vipmanager.ser
Now the program should be able to save properly. We choose to save the files when we press "exit" in each menu.


You can start running the program now.


After you run the program make sure to comment out line 37 in LogInPresenter again because we don't want to create the same account twice.
If the files are saved properly you don't need to create this organizer account again.

Here is the link to the video demo:
https://drive.google.com/drive/folders/1B8egksW0SGTWtRdMvI1swhcJZbVxEiTr?usp=sharing
Additional Note: Run the program on an API 30 Android Device