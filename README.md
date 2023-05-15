# n-tkom
This is the code for a small programming project for the KTH course IK1203 Networks and Communications from the spring semester 2023.
Task 1 was to implement a simple TCP client as a Java class. The TCP client is able to set a connection to a server, send data to the server and return the answer as a result.
In task 2,the possibility for a timeout and data limit was added to the TCP client.
For task 3, a new class called HTTPAsk was implemented which uses the TCPClient from task 2. This is a web server that takes a HTTP GET request, and returns the corresponding HTTP response.
In task 4, the class HTTPAsk class from task 3 was changed to work concurrently, i.e. the server is able to handle multiple clients in parallel.
