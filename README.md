# Project 1 - *ToDo App*

**ToDo App** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Longji Jerry Chen**

Time spent: **33** hours spent in total (Excluding roughly 2-3 hours helping others on the Slack)

## User Stories

The following **required** functionality is completed:

* [ ] User can **view a list of todo items**
* [ ] User can **successfully add and remove items** from the todo list
* [ ] User's **list of items persisted** upon modification and and retrieved properly on app restart

The following **optional** features are implemented:

* [ ] User can **tap and hold a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list

The following **additional** features are implemented:

* [ ] User's data is managed by SQLite Database to include more complex data than simple Task names
* [ ] User input includes Month, Date, and Year so that users can add a due date to a task
* [ ] User input includes input validation, checking if the taskField is empty or not and if the date is a valid date or not
* [ ] User date is sanitized, allowing for dates to be properly displayed (i.e. month is stored as integers from 1 to 12 mapped to different string values for their corresponding month, and the day and year cannot be displayed without a valid month)
* [ ] Clear all tasks button allowing for users to get rid of every single task easily
* [ ] Improved UI, including intuitive delete buttons for each task 
* [ ] Update activity and page displays the selected task's information to allow users an intuitive way to update their task. The information is also validated, meaning if there is an invalid number, the existing data is not displayed and instead the input field **hint** is displayed.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='./app_walkthrough.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

### The main challenges I encountered while working on this app revolved around storing more than just the task name. Implementing a card-like approach required 3 main things that I found some difficulty with:

### 1. Storing the data

Storing the data of string task names were covered in the tutorial using FileUtils and writing to a text data file; however, with multiple data fields per data point, using this method would not suffice. Thus, I had to find a way to store more complex data in a table format. I first tried using ROOM database, however, I quickly ran into some issues and decided that it was in my best interest to look for a different method. Ultimately, I came across SQLite which was relatively simple to use and didn't have too many complexities regarding the storage--perfect for storing a simple task card. Changing the program to store and load items from a text file to an SQL database was quite challenging, but I ultimately figured out how to access the database using MyDBHelper (Custom database kt file) using writable/redable database and rawQuery. Overall, I learned how to use a simple database and how to access the database using rawQuery.

### 2. Creating a custom adapter to display the Task Cards

The original task list in the base todo app was very bland and had no real structure. Each list item did not have a real distinction between one item and the next, but rather, they were simply separated by spacing. In order to implement a more user intuitive task list that also included due dates, I had to implement a custom adapter to display the multiple data fields in a nice layout. The main challenge regarding this process involved accessing multiple views based on the input field (i.e. taskName, dayInput, yearInput, monthInput). Initially I could not access the specific ID's and consequentially the input fields; however, I eventually figured out that I had to initialize the views in the Inner ViewHolder class in order to access them. Ultimately, I gained a deeper understanding of how the layouts work together with the activity files of the app.

### 3. Creating a new activity to prompt users to edit their task and due dates

This was definitely the most difficult and most time-consuming part of developing this app. Creating a new user prompt to edit pre-existing task cards involved managing the custom adapters, the database, as well as two individual activity files (Both the MainActivity and UpdateCard kt files). At first, the main challenge was prompting the user for an update. However, it was quickly simple to instantiate a new activity using Intent. The next steps involved passing information to the second activity. Because the new prompt should update a specific task card and only that specific task card, I had to pass some value that would let the second activity know which task card to manipulate. This involved a few steps as I first passed the task card name to the savedInstanceState of the second activity, but it quickly became obsolete as the data wouldn't necessarily always be accessible using getSerialized. Eventually, I passed the autogenerated TASKCARDID of the corresponding task card so that the second activity could directly access the database which was global to all activities, rather than allowing the second activity to manipulate the listOfCards initialized in MainActivity. After passing the TASKCARDID, I could now query and access the specific row of task card data. However, I ran into an issue where the Cursor only had one column (the task name). I eventually downloaded the SQLite browser and checked the database file within my app. I found that there was nothing wrong with my database itself, so I deduced that it must be something to do with my querying. Ultimately, I found out that rawQuery offers no column validation and that it was probably the root of the issue. Thus, I tried to use query to find the specific row and resolved the issue.

Now, my app was fully working; however I ran into one more issue. The list did not update after editing the task, because the list was not loaded. So, I had to use startActivityForResult in order to set like a timer effect that calls loadItems() when I receive a result (from finishing the UpdateCard activity). I did use some "cheat code" that wasn't optimized and also used some depreciated methods. However, with guidance from Codepath, I'll be happy and excited to develop clean codebases in the future.

## License

    Copyright [2022] [Longji Chen]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
