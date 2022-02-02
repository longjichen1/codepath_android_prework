# Project 1 - *ToDo App*

**ToDo App** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Longji Jerry Chen**

Time spent: **33** hours spent in total

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
* [ ] Improved UI
* [ ] Update activity and page displays the selected task's information to allow users an intuitive way to update their task. The information is also validated, meaning if there is an invalid number, the existing data is not displayed and instead the input field **hint** is displayed.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/link/to/your/gif/file.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

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
