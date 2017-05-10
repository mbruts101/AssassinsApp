Contributions to the project:

- Visualizing how the various contributions of the group members will work together
- Implemented the basic structure of the Client to Server communication 
  - Server launch of ServerSocket to accept the Client Socket connections 
    - Hand these connections off the GameLogic of the application to determine which messages to send when
  - Client class to connect to the server and create a client listener to recieve messages from the server
    - Hand clientlistener messages off the Google Maps API
    - Send messages to the server according to "Kill" button and Google Maps API callbacks
  
