# 🏥 Hospital Management System

Technology plays an increasingly important role in the delivery of healthcare services. Despite all
advancements, many healthcare facilities still rely on outdated systems for managing patient information and
scheduling, leading to inefficiencies and increased risk of errors. This project aims to develop a Hospital
Management System that leverages modern software architecture and design patterns to streamline the
registration and data-sharing processes for doctors and patients while focusing on security and integrity of
handled medical data.

<hr>

## Requirements
In order to design efficient management system it is important to clarify all requirements of the system, both
functional and non-functional.

### Functional requirements
#### 1. User authentication
The system must provide a secure login mechanism for both doctors and patients.
#### 2. Doctor & Patient registration
   Doctors and patients must be able to register in order to get in the system. In initial MVP we will not add
additional attributes to each, but in later iterations we may add field better identifying their role, eg.
providing medical license number and specialty for doctors and providing birth number for patients.
#### 3. Doctor selection
   Patients must be able to browse a list of available doctors and select one. In future iterations we may add
additional information to the list, e.g. specialty, ratings, or availability.
#### 4. Data sharing among doctors
   Doctors must be able to securely share patient data with other doctors within the same healthcare facility.
#### 5. Data manipulation
   The selected doctor must be able to add, edit, or delete patient data such as medical history, prescriptions,
and test results.

<hr>

### Non-functional requirements
#### 1. Security
All data must be encrypted during transmission and storage. Passwords are stored in hashed form to protect it
even if the database experienced a breach. 
#### 2. Usability
The user interface must be intuitive and accessible, requiring minimal training for end-users.
#### 3. Compliance
The system must comply with healthcare regulations and standards, including parts of GDPR for patient data
privacy.

<hr>

## Technical specification
This technical specification gives a overall picture of the tech stack, aiding both the development team
and any future contributors in understanding the system's technical landscape.

### Backend
**Programming Language:** Java <br/>
**Framework:** Spring Boot (Version: 2.6.0) <br/>
**Database:** MySQL (Version: 8.0.26) <br/>

Spring Boot is chosen for its ease of use, extensive community support, and built-in features like
security and data access. MySQL serves as the relational database management system, providing a
reliable and efficient way to manage data.

### Frontend
**Programming Language:** HTML 5, CCS 3

For this project, the frontend is intentionally kept simple and is developed using HTML and CSS. Given
the straightforward nature of the Hospital Management System's user interface requirements, a more
complex frontend framework was deemed unnecessary. HTML provides the structure for the user
interface, while CSS is used for styling, ensuring that the application is both functional and visually
appealing. By focusing on HTML and CSS, the team was able to prioritize backend functionalities and
security features, while still delivering a user-friendly interface.

<hr/>

## Deployment
The application is currently running locally for development and testing purposes. However, it is
designed to be easily deployable to a cloud-based or on-premises server in the future.

<hr/>

## Software and architecture patterns used 
This section discusses the architecture and design patterns that are relevant to the development of the
Hospital Management System. While some of these are implemented by default due to the technologies
we used, others are suggestions for potential future enhancements.

<hr/>

## Architecture Patterns
**Model-View-Controller (MVC)** <br/>
Spring Boot inherently follows the MVC architecture, separating the
application into Model, View, and Controller components. This allows for cleaner code organization and
easier maintenance.

<hr/>

## Design Patterns
**Singleton Pattern** <br/>
Spring Boot manages singletons by default for its beans, ensuring that only one
instance exists for each bean in the Spring container. This is particularly useful for services that require
a single point of control, such as logging or database access.

**Factory Method Pattern** <br/>
While not implemented, this pattern could be used for creating different
types of user accounts (Doctor, Patient, Admin), allowing for more flexible and maintainable code. 

**Observer Pattern** <br/>
This could be beneficial for implementing real-time notifications within the system.
For example, doctor could be notified when new document was shared with him/er.

**Decorator Pattern** <br/>
This pattern could be used to add additional functionalities to objects without
altering their structure. For instance, adding new types of user permissions or features could be done
more easily.

<hr/>
<div align="center">
 
| Team Member                                                                    | Responsibilities                                                                                                                         |
|----------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------|
|**Tomáš Komora** |**Security Design and Visualisation** <br/> <ul><li>Implement secure authentication and authorisation</li> <li>Ensure data encryption</li> <li>Create diagrams</li></ul>|
|**Eva Nedeliaková** |**Design and Documentation** <br/> <ul><li>Create the system architecture</li> <li>Document the project specifications</li> <li>Conduct security audit and test</li></ul>|
|**Samuel Hančák** |**Implementation (Frontend)** <br/> <ul><li>Develop the user interface</li> <li>Implement client-side functionalities</li> <li>Ensure usability and accessibility</li></ul>|
|**Filip Mikuš** |**Implementation (Backend)** <br/> <ul><li>Develop the server-side logic</li> <li>Implement database interactions</li> <li>Ensure system performance and scalability</li></ul>|

</div>



 
