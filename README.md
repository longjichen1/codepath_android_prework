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

<img src='http://i.imgur.com/link/to/your/gif/file.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

### The main challenges I encountered while working on this app revolved around storing more than just the task name. Implementing a card-like approach required 3 main things that I found some difficulty with:

### 1. Storing the data

Storing the data of string task names were covered in the tutorial using FileUtils and writing to a text data file; however, with multiple data fields per data point, using this method would not suffice. Thus, I had to find a way to store more complex data in a table format. I first tried using ROOM database, however, I quickly ran into some issues and decided that it was in my best interest to look for a different method. Ultimately, I came across SQLite which was relatively simple to use and didn't have too many complexities regarding the storage--perfect for storing a simple task card. Changing the program to store and load items from a text file to an SQL database was quite challenging, but I ultimately figured out how to access the database using MyDBHelper (Custom database kt file) using writable/redable database and rawQuery. Overall, I learned how to use a simple database and how to access the database using rawQuery.

### 2. Creating a custom adapter to display the Task Cards

The original task list in the base todo app was very bland and had no real structure. Each list item did not have a real distinction between one item and the next, but rather, they were simply separated by spacing. In order to implement a more user intuitive task list that also included due dates, I had to implement a custom adapter to display the multiple data fields in a nice layout. The main challenge regarding this process involved accessing multiple views based on the input field (i.e. taskName, dayInput, yearInput, monthInput). Initially I could not access the specific ID's and consequentially the input fields; however, I eventually figured out that I had to initialize the views in the Inner ViewHolder class in order to access them. Ultimately, I gained a deeper understanding of how the layouts work together with the activity files of the app.

### 3. Creating a new activity to prompt users to edit their task and due dates

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
